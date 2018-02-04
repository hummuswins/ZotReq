from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup
import re

def separate(prereqs):
    prereqs = re.sub(r'[a-z=();]', '', prereqs)
    prereqs = ' '.join(prereqs.split())
    prereqs = prereqs.split('AND')
    for x in range(len(prereqs)):
        prereqs[x] = prereqs[x].split('OR')

    #in the future we may need to represent the AND / OR relationship as a tree
    #initially we can split them by the AND's to turn them into lists
    #within these lists are separate sections of required classes 
    #e.g) ['I&C SCI 22 C OR CSE 22 C OR I&C SCI H22 C OR I&C SCI 33 C OR CSE 43 C OR AP COMP SCI AB 4 ', ' I&C SCI 45C C OR CSE 45C C ', ' MATH 6G C OR MATH 3A C OR I&C SCI 6N C']
    #after that we can make this list into a list of lists by .split('OR'), yielding:
    #[['I&C SCI 22 C ', ' CSE 22 C ', ' I&C SCI H22 C ', ' I&C SCI 33 C ', ' CSE 43 C ', ' AP COMP SCI AB 4 '], [' I&C SCI 45C C ', ' CSE 45C C '], [' MATH 6G C ', ' MATH 3A C ', ' I&C SCI 6N C']]
    #can easily represent this [[str]] through [str] -> str tree

    prereqs = str(prereqs)
    prereqs = re.sub(r"[\[*\]*\'*]", '', prereqs)
    
    return prereqs


'''
    for p in prereqs:
        p = re.sub(r'[a-z()=;]', '', p)
        p = ' '.join(p.split())
        p = p.split('AND')
        for x in range(len(p)):
            p[x] = p[x].split('OR')
'''

        #take out all AND's, OR's, (p), =, ; 
        #ics22, ics69, 

uClient = None

my_url = "https://www.reg.uci.edu/cob/prrqcgi?term=201803&dept=COMPSCI&action=view_by_term#112"

filename = "courses.csv"
headers = ("Course, Title, Prereqs\n")

f = open(filename, 'w')
f.write(headers)

try:
    uClient = uReq(my_url)
    page_html = uClient.read()

finally:
    if uClient != None:
        uClient.close()

page_soup = soup(page_html, "html.parser")
class_courses = page_soup.findAll("td", {"class": "course"})
class_titles = page_soup.findAll("td", {"class": "title"})
class_prereqs = page_soup.findAll("td", {"class": "prereq"})

prereqs = class_prereqs[0].text.strip()
print(separate(prereqs))

for x in range(len(class_courses)):
    course = class_courses[x].text.strip()
    title = class_titles[x].text.strip()
    prereqs = class_prereqs[x].text.strip()

    f.write(course + ',' + title + ',' + separate(prereqs) + "\n")

f.close()



