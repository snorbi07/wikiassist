DROP VIEW IF EXISTS `v_reference`;
CREATE VIEW `v_reference` AS
  SELECT
    page.page_id,
    page.page_title,
    (SELECT page_id
     FROM page
     WHERE
       page.page_title = pagelinks.pl_title AND page.page_namespace = pagelinks.pl_from_namespace) AS page_reference_id,
    pagelinks.pl_title                                                                             AS page_reference_title
  FROM page
    LEFT JOIN (pagelinks) ON (page.page_id = pagelinks.pl_from)
  # We only care about the 'Main'/'Content' namespace, the rest is ignored
  WHERE (page.page_namespace = 0) AND (pagelinks.pl_from_namespace = 0)
        AND (pagelinks.pl_title IS NOT NULL);

# We 'materialize' it in order to further improve the access speed (eliminate sub-query and add indexing).
# We also drop the empty titles and IDs, since those refer to the 'Create missing entry' page.
DROP TABLE IF EXISTS `references`;
CREATE TABLE `references` AS
  SELECT *
  FROM v_reference WHERE page_reference_id IS NOT NULL AND page_reference_title IS NOT NULL;

DROP INDEX IF EXISTS `idx_references` ON `references`;
CREATE INDEX `idx_references`
  ON `references` (page_id, page_title)
  ALGORITHM DEFAULT LOCK = NONE;

DROP VIEW IF EXISTS `v_top_referred`;
CREATE VIEW `v_top_referred` AS
  SELECT
    page_reference_id,
    COUNT(*) AS num_referals
  FROM `references`
  WHERE page_reference_id IS NOT NULL
  GROUP BY page_reference_id
  ORDER BY num_referals DESC;
