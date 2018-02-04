from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup
import re, os.path

def separate(prereqs):

    #in the future we may need to represent the AND / OR relationship as a tree
    #initially we can split them by the AND's to turn them into lists
    #within these lists are separate sections of required classes 
    #e.g) ['I&C SCI 22 C OR CSE 22 C OR I&C SCI H22 C OR I&C SCI 33 C OR CSE 43 C OR AP COMP SCI AB 4 ', ' I&C SCI 45C C OR CSE 45C C ', ' MATH 6G C OR MATH 3A C OR I&C SCI 6N C']
    #after that we can make this list into a list of lists by .split('OR'), yielding:
    #[['I&C SCI 22 C ', ' CSE 22 C ', ' I&C SCI H22 C ', ' I&C SCI 33 C ', ' CSE 43 C ', ' AP COMP SCI AB 4 '], [' I&C SCI 45C C ', ' CSE 45C C '], [' MATH 6G C ', ' MATH 3A C ', ' I&C SCI 6N C']]
    #can easily represent this [[str]] through [str] -> str tree

    prereqs = re.sub(r'[a-z=()&;]|\sC\s|\sD\s|\s3\s|\s4\s|\s5\s', '', prereqs)
    prereqs = ' '.join(prereqs.split())
    prereqs = prereqs.split('OR')
    for x in range(len(prereqs)):
        prereqs[x] = prereqs[x].replace(" ANDUPPER DIVISION STANDING ONLY", '')
        prereqs[x] = prereqs[x].replace(" AND ", ' |AND| ')
        prereqs[x] = prereqs[x].split('|')

    prereqs = str(prereqs)
    prereqs = re.sub(r"[\[*\]*\'*]", '', prereqs)    
    prereqs = re.sub(r"^\s|\s{2,}", '', prereqs)
    prereqs = re.sub(r"\s,", ',', prereqs)    
    return prereqs


departments = ["AC+ENG", "AFAM", "ANTHRO", "ARABIC", "ART", "ART+HIS", "ART+STU"
                "ARTS", "ARTSHUM", "ASIANAM", "BIO+SCI", "BME", "BSEMD", "CBEMS",
                "CHEM", "CHINESE", "CLC%2FLAT" "COGS", "COM+LIT", "COMPSCI", "CRITISM", "CRM%2FLAW",
                "CSE","DANCE","DEV+BIO","DRAMA","E+ASIAN","EARTHSS","ECO+EVO","ECON",
                "ECPS", "EDUC", "EECS", "ENGLISH", "ENGR", "ENGRCEE", "ENGRMAE", "ENGRMSE", 
                "ENVIRON", "EPIDEM", "EURO+ST", "FILMSTD", "FLM%26MDA" "FRENCH", "GEN%26SEX" 
                "GERMAN", "GLBLCLT", "GREEK", "HEBREW", "HINDI", "HISTORY", "HUMAN", "HUMARTS", "I%26C+SCI",
                "IN4MATX", "INTL+ST", "ITALIAN", "JAPANSE", "KOREAN", "LATIN", "LAW", 
                "LINGUIS", "LIT+JRN", "LPS", "MATH", "MED+HUM", "MGMT", "MGMT+EP", 
                "MGMT+FE", "MGMTMBA", "MOL+BIO", "MUSIC", "NET+SYS", "NEURBIO",
                "NUR+SCI", "PATH", "PERSIAN", "PHILOS", "PHRMSCI", "PHY+SCI", "PHYSICS", 
                "POL+SCI", "PORTUG", "PP%26D", "PSY+BEH", "PSYCH", "PUBHLTH", "REL+STD", 
                "ROTC", "RUSSIAN", "SOC+SCI", "SOCECOL", "SOCIOL", "SPANISH", "SPPS", 
                "STATS", "TOX", "UNI+STU", "VIETMSE", "WOMN+ST", "WRITING", 
]

for key in departments:
    uClient = None
    my_url = "https://www.reg.uci.edu/cob/prrqcgi?dept=" + key + "&action=view_by_term&term=201803#112"
    filename = key+"_courses.csv"

    f = open(filename, 'w')
    
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

    for x in range(len(class_courses)):
        course = class_courses[x].text.strip()
        course = ' '.join(course.split())
        course = re.sub('[&;]', '', course)
        title = class_titles[x].text.strip()
        prereqs = class_prereqs[x].text.strip()

        f.write(course + ',' + title + ',' + separate(prereqs) + "\n")

    f.close()



