# CS4990 Project 1 - Dr. Ben Steichen
# By William Hang and Kevin Yao

# How to run this program?
This project consists of three main components: the Crawler, CrawlLeg, and the Indexer.
The Main class contains the driver for the Crawler and the Indexer.

- To run this program, specify one seed URL in the Main class for the Crawler's constructor.
- Set the amount of pages you would like to visit in the Crawler's in the search() function.
- Additionally, change the /repository and /body_text directories in the Crawler class to your own designated storage folders.
- Specify your own report.csv File and your own index_list.csv File in the Indexer class.
- Once all these values are changed, you may begin the Main driver and watch your crawler visit webpages, download the HTML documents, and enqueue any out-links.