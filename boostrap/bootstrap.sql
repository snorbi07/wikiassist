#CREATE TABLE IF NOT EXISTS `references` (
#	`reference_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
#	`page_id` int(8) unsigned NOT NULL,
#	`page_title` varbinary(255) NOT NULL,
#	`page_namespace` int(11) NOT NULL,
#	`page_reference_id` int(8) unsigned NOT NULL,
#	`page_reference_title` varbinary(255) NOT NULL,
#	`page_reference_namespace` int(11) NOT NULL,
#PRIMARY KEY (`reference_id`),
#KEY(`page_title`, `page_id`)
#);

DROP VIEW IF EXISTS `v_reference`;
CREATE VIEW `v_reference` AS SELECT 
	page.page_id, 
	page.page_title,
	(SELECT page_id FROM page WHERE page.page_title = pagelinks.pl_title AND page.page_namespace = pagelinks.pl_from_namespace) AS page_reference_id,
	pagelinks.pl_title AS page_reference_title
FROM page
LEFT JOIN (pagelinks) ON (page.page_id=pagelinks.pl_from)
# We only care about the 'Main'/'Content' namespace, the rest is ignored
WHERE (page.page_namespace = 0) AND (pagelinks.pl_from_namespace = 0) 
AND (pagelinks.pl_title IS NOT NULL);

# We 'materialize' it in order to further improve the access speed (eliminate sub-query and add indexing)
DROP TABLE IF EXISTS `references`;
CREATE TABLE `references` AS SELECT * FROM v_reference;
DROP INDEX IF EXISTS `idx_references` ON `references`;
CREATE INDEX `idx_references`  ON `references` (page_id, page_title) ALGORITHM DEFAULT LOCK=NONE


#SELECT * FROM v_reference WHERE page_id = 140590 AND page_reference_id IS NOT NULL;

#SELECT 
#page.page_id, 
#CAST(page.page_title as char character set utf8) AS page_title,
#(SELECT page_id FROM page WHERE page.page_title = pagelinks.pl_title AND page.page_namespace = pagelinks.pl_from_namespace) AS page_reference_id,
#CAST(pagelinks.pl_title as char character set utf8) AS page_reference_title
#FROM page
#LEFT JOIN (pagelinks) ON (page.page_id=pagelinks.pl_from)
#WHERE (page.page_namespace = 0) AND (pagelinks.pl_from_namespace = 0);


#SELECT 
#page.page_id, 
#page.page_title,
#(SELECT page_id FROM page WHERE page.page_title = pagelinks.pl_title AND page.page_namespace = pagelinks.pl_from_namespace) AS page_reference_id,
#pagelinks.pl_title AS page_reference_title
#FROM page
#LEFT JOIN (pagelinks) ON (page.page_id=pagelinks.pl_from)
#WHERE (page.page_id > 999) AND (page.page_namespace = 0) AND (pagelinks.pl_from_namespace = 0)
#ORDER BY page.page_id LIMIT 10000;





