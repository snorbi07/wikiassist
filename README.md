# Development notes

- Crawling solution works, but does not scale. Fetching the references for a single page is fine, however performance
is serverly capped by both network IO and Wikipedia's rate limiter, which blocks network requests if they come more frequently than ~50ms.

- Wikipedia provides a periodic dump of their DB, this could be used as well, instead of crawling.
For Wikiassist, the whole data dump (https://dumps.wikimedia.org/simplewiki/latest/simplewiki-latest-pages-articles.xml.bz2) is not needed.
The page (https://www.mediawiki.org/wiki/Manual:Page_table) and page links SQL tables contain everything we need (https://www.mediawiki.org/wiki/Manual:Pagelinks_table)
Thus, the following 2 links are needed:
https://dumps.wikimedia.org/simplewiki/latest/simplewiki-latest-page.sql.gz
https://dumps.wikimedia.org/simplewiki/latest/simplewiki-latest-pagelinks.sql.gz

https://en.wikipedia.org/wiki/Wikipedia:Database_download

- Make sure to use the correct Database server (the dumps contain the exact version of the database server used)
```
-- MySQL dump 10.13  Distrib 5.5.47, for debian-linux-gnu (x86_64)
--
-- Host: 10.64.48.20    Database: enwiki
-- ------------------------------------------------------
-- Server version	5.5.5-10.0.33-MariaDB
```

- At the time of writing, for importing Wikipedia tables MariaDB version 10.0.34 works.

If you try to use some fancy SQL admin tool, such as MySQL Workbench, you can easily end up with a failure half-way through: 
```
11:04:43	INSERT INTO `page` VALUES (14270297,3,'Anver0819','',0,0,0,0.747669239264,'20170408094635','20170317210057',171840701,4030,'wikitext',NULL),(14270301,0,'FA_Cup_Final_2007','',0,1,1,0.041543226041,'20171017171212',NULL,171840100,31,'wikitext',NULL),(14270304,3,'75.41.28.128','',0,0,1,0.32195753536,'20150210174705',NULL,171840143,835,'wikitext',NULL),(14270306,3,'203.197.115.80','',0,0,0,0.39322004304,'20180228152421','20171208132750',708875480,6,'wikitext',NULL),(14270307,3,'74.170.139.197','',0,0,0,0.323748052918,'20150421122047',NULL,172396756,1898,'wikitext',NULL),(14270308,0,'Nilo_Syrtis','',0,0,0,0.8548686982289999,'20180204172216','20180121064046',790771762,2194,'wikitext',NULL),(14270309,4,'Village_pump_(policy)/Archive_4','',0,0,0,0.790741949461,'20180228152421','20180108195651',172655255,66249,'wikitext',NULL),(14270312,10,'Ct_link/doc','',0,0,0,0.9157336480649999,'20180228152421','20171208132630',729906857,2539,'wikitext',NULL),(14270313,4,'Village_pump_(proposals)/Archive_4','',0,0,0,0.520435769888,'20180214022031','20180108125010',390501345,75157,'wikitext',NULL),(14270314,3,'4.231.94.106','',0,0,0,0.403503719991,'20180228152421','20171208132913',708888271,6,'wikitext',NULL),(14270315,1,'Superette','',0,0,0,0.470912218536,'20180228152421','20171030072435',721514490,764,'wikitext',NULL),(14270316,0,'Ron_E._Paul','',0,1,1,0.893115638852,'20180206065720',NULL,171840228,22,'wikitext',NULL),(14270317,0,'Nilosyrtis','',0,1,1,0.021598169129,'20170716024425',NULL,171840253,25,'wikitext',NULL),(14270318,3,'Transgender_info','',0,0,1,0.272636970278,'20150311212727',NULL,171840256,2445,'wikitext',NULL),(14270319,3,'76.81.209.47','',0,0,1,0.605783648588,'20150210174705',NULL,171840265,1393,'wikitext',NULL),(14270320,1,'Namerikawa,_Toyama','',0,0,0,0.296913754949,'20180228152421','20171114121943',781776182,95,'wikitext',NULL),(14270322,1,'Patawalonga_River','',0,0,0,0.446494174836,'20180228152421','20180110053805',819586622,7043,'wikitext',NULL),(14270326,0,'Marchington_Lake_(Ontario)','',0,0,0,0.809256383887,'20180217152544','20171231052131',679455916,3812,'wikitext',NULL),(14270327,0,'Cdesign_proponentsists','',0,1,0,0.65886298417,'20180301042725',NULL,175177015,74,'wikitext',NULL),(14270328,0,'James_Southworth','',0,0,0,0.07724826845,'20180222020543','20180212005742',825197718,5152,'wikitext',NULL),(14270331,1,'Funahashi,_Toyama','',0,0,0,0.170588534222,'20180228152421','20171117030208',775690166,95,'wikitext',NULL),(14270332,1,'Helitack/Comments','',0,1,0,0.9572252019609999,'20180125211907','20160430220402',717987454,27,'wikitext',NULL),(14270334,0,'Yamaha_YM1','',0,0,0,0.7786958703310001,'20180228152421','20171206102502',755336899,1394,'wikitext',NULL),(14270335,0,'Dirty_dirk_fischer','',0,1,0,0.90864820392,'20180301214424',NULL,171862234,36,'wikitext',NULL),(14270337,1,'James_Southworth','',0,0,0,0.188196052557,'20180228152421','20171212082826',776014141,1278,'wikitext',NULL),(14270339,3,'68.122.3.182','',0,0,1,0.897645826357,'20150210174705',NULL,171840463,1516,'wikitext',NULL),(14270340,3,'Cheezyyman','',0,0,0,0.418944288687,'20171230003554','20170319184241',422260493,10731,'wikitext',NULL),(14270341,0,'Compulsive_buying_disorder','',0,0,0,0.1436664564,'20180228152421','20180209035729',812723578,17232,'wikitext',NULL),(14270342,1,'Kamiichi,_Toyama','',0,0,0,0.533785230246,'20180228152421','20171117031205',775690462,95,'wikitext',NULL),(14270343,0,'Tokyu_7000_series','',0,0,0,0.573472486353,'20180301102419','20180217163740',818681511,5048,'wikitext',NULL),(14270344,1,'Tateyama,_Toyama','',0,0,0,0.20025979654,'20180228152421','20171117031205',775690716,95,'wikitext',NULL),(14270346,3,'203.177.242.225','',0,0,0,0.261164126545,'20171013135734','20170412115400',374806308,5691,'wikitext',NULL),(14270348,1,'Kurobe,_Toyama','',0,0,0,0.192391463134,'20180228152421','20171117030741',778878493,96,'wikitext',NULL),(14270349,3,'Edy2007~enwiki','',0,0,0,0.6894979800809999,'20150422122821','20170412115305',658297993,5490,'wikitext',NULL),(14270350,3,'71.245.243.176','',0,0,1,0.136033779823,'20150210174705',NULL,171840610,697,'wik...	Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near '' at line 1	0.047 sec
```

Just use the provided CLI. The downloaded dump files can be loaded into the database with the following command:
```
mysql -u root -p simplewiki < simplewiki-latest-page.sql.sql
mysql -u root -p simplewiki < simplewiki-latest-pagelinks.sql
```

# Assumptions
- We only care about Wikipedia references, all other references (pictures, scripts, movies, ...) are ignored
- A static snapshot/dump of the Wikipedia is used and is not kept in sync with the live one. Hence the 'reporting table' approach instead of materialized view.
- Only the main namespace pages are traversed (https://en.wikipedia.org/wiki/Wikipedia:Namespace) 