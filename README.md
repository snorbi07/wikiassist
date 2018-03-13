# Assumptions

- https://simple.wikipedia.org/ is used
- We only care about Wikipedia references, all other references (pictures, scripts, movies, ...) are ignored
- A static snapshot/dump of the Wikipedia is used and is not kept in sync with the live one. Hence the 'reporting table' approach instead of materialized view.
- Only the main namespace pages are traversed (https://en.wikipedia.org/wiki/Wikipedia:Namespace)


# Setup

The solution relies on replicating the Wikipedia database and uses that to bootstrap the references API. 

## Database setup

Install MariaDB and start it. The WikiAssist application expects it to run on `localhost:3306`, with the database name of `simplewiki`.
Currently the application uses the `root` user with an empty password ("").  
Make sure to use the correct Database server (the dumps contain the exact version of the database server used). At the time of writing, for importing Wikipedia tables MariaDB version 10.0.34 was used.
```
-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 10.64.48.20    Database: enwiki
-- ------------------------------------------------------
-- Server version	5.5.5-10.0.33-MariaDB
```

Once the installation is done load the Wikipedia SQL dumps. The [page table](https://www.mediawiki.org/wiki/Manual:Page_table) and [page links](https://www.mediawiki.org/wiki/Manual:Pagelinks_table) SQL tables contain the needed data.
To fetch them, run:
```
wget https://dumps.wikimedia.org/simplewiki/latest/simplewiki-latest-page.sql.gz 
wget https://dumps.wikimedia.org/simplewiki/latest/simplewiki-latest-pagelinks.sql.gz
```

The downloaded dump files can be loaded into the database with the following command:
```
mysql -u root -p simplewiki < simplewiki-latest-page.sql.sql
mysql -u root -p simplewiki < simplewiki-latest-pagelinks.sql
```

## Database bootstrapping

One the Wikipedia database is imported, run the provided [bootstrap.sql](https://github.com/snorbi07/wikiassist/blob/master/boostrap/bootstrap.sql) script.
```
mysql -u root -p simplewiki < bootstrap/bootstrap.sql
```

This creates the table which contains the `references`, besides the `pages` and `page_links`.
Currently, this means ~400K pages (nodes) and 5M references (edges) for the main namespace.

## Compiling the application
To compile the application, just run:
`
./gradlew build
`

## Running the application
`
./gradlew run
`
This will trigger a cycle search for the [Nothing page](https://simple.wikipedia.org/wiki/Nothing).
If the database is setup correctly, the application will start prefetching the data and when done trigger the actually 
traversal.