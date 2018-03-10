# Notes

- Wikipedia provides a periodic dump of their DB, this could be used as well, instead of crawling, however we download a lot of unnecessary data as well this way
https://dumps.wikimedia.org/enwiki/latest/enwiki-latest-pages-articles.xml.bz2

# Assumptions

- All links are gathered from the Wikipedia pages, regardless of the structure of the page
- We only care about HTML pages, all other references (pictures, scripts, movies, ...) are ignored