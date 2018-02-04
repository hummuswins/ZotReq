from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup
import re

def write_courses(courses) -> None:

    for x in range(len(courses)):
        #course_name = ' '.join(courses[x].text.split())
        course_name = courses[x].text.split('.')
        course_name[0] = re.sub('\s', ' ', course_name[0])
        desc = descriptions[x].text.split('\n')

        try:
            f.write(course_name[0] + ',' + desc[1] + '\n\n')
        except UnicodeEncodeError:
            pass

def get_description(descriptions) -> str:
        
    for desc in descriptions:
        desc = desc.text.split('\n')
        f.write(desc[1])
        print(desc[1])
        
        return desc

uClient = None
my_url = "http://catalogue.uci.edu/donaldbrenschoolofinformationandcomputersciences/#courseinventory"

filename = "zCOMPSCI_desc.csv"
f = open(filename, 'w')

header = "Course, Course Description"

try:
    uClient = uReq(my_url)
    page_html = uClient.read()

finally:
    if uClient != None:
        uClient.close()

page_soup = soup(page_html, "html.parser")


courses = page_soup.findAll("p", {"class": "courseblocktitle"})
descriptions = page_soup.findAll("div", {"class": "courseblockdesc"})
print(len(descriptions))


write_courses(courses)

f.close()