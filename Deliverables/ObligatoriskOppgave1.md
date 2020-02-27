# Deloppgave 1
## Kartlegging av kompetanse
### Lise og Sol
-   Programmering: Behersker godt java, erfaring fra INF101 og INF102. 
-   Git: brukt litt i INF101
-   Tester: brukt litt i INF101

### Sigmund
-   Programmering: Behersker godt java, bakgrunn i kryptografi.
-   Git: Ny til git.
-   Tester: Litt ny til tester

### Tim
-   Programmering: behersker godt java, python, webutikling/webdesgin
-   Git: Har brukt git i mange år
-   Tester: Brukt litt tester primært i INF101, INF102
-   Har arbeidserfaring med systemkonstruksjon fra tidligere sommerjobb. 

### Thomas
-   Programmering: Behersker godt java, python
-   Git: Har brukt litt i INF101
-   Tester: Brukt TDD i INF101

## Rollefordeling
### Tim - Gruppeleder
-   Hovedansvar for Git-repo'en. At alt blir gjort på en oversiktig og god måte.
    -   Hjelpe gruppen med å forstå Git og hvordan man bruker det.
    
-   Ansvar for vedlikehold av project-board'en.
    -   Holde den oppdatert
    -   Holde orden på Issues
    
-   Passe på at alle får hjelp til det de holder på med.

### Sol - Kundekontakt
-   Kommuniserer med vår kunde (Siv (og evt. gruppeleder))
    -   Stiller spørsmål angående oppgavene vi blir tildelt.

### Researcher
-   Sette seg inn i et tema og fungere som orakel for gruppen.

#### Thomas - Game Master/Hovedtester
-   Hovedansvar for å forstå spillet, og hjelpe resten av gruppen og forstå spillet.

#### Sigmund - LibGdx 
-   Hovedansvar for å forstå spillmotoren.
  
## Lise - Møteansvarlig
-   Ansvar for å følge agenda.
-   Skrive møtereferat, og lagde agenda for neste møte.

## Deloppgave 2
Overordnet mål: Vi skal lage en digital versjon av brettspillet roborally. 

### Krav til systemet
-   Spillet skal fungere plattformuavhengig.
-   Brettet og nødvendig spillinformasjon skal visualsering. Hva som er nødvendig må defineres. 
-   Spillet skal kunne spilles flerspillere over LAN, opp til 8 spillere.
-   Spillet skal ha implementert AI spillere

### Prioriteringsliste til første iterasjon
1.  Spillbrett object
2.  Spiller object
3.  Enkel visalisering av brett og spiller
4.  Flytte spiller i spillbrettet
5.  Rotere spiller i spillbrettet

## Deloppgave 3
### Valg av prosjektmetodikk
#### Elementer fra XP
-   **User stories**: Blir lettere å snakke rundt funksjonaliteter, samtidig som det direkte gjør det lettere å følge Single Responsibility Principle (vi blir vurdert på dette i koden)
-   **Test-Driven Development**: All produksjonskode skal klare å løse en test som ikke virket fra før. 
-   **Collective Ownership**: Ingen har noe mer autoritet over en modul/teknologi enn andre.
-   **Continous Integration**: Den første som checker inn "vinner". Alle andre merger
-   **Simple Design**: Avgrenset fokus på "user stories" i den iterasjonen vi jobber i. Legge til infrastruktur utelukkende når det er behov for det. 
-   **You Ain't Gonna Need It**: Alltid anta du ikke kommer til å trenge noe, ved mindre det er overbevisende bevis for at det er mer cost-effektivt å implementere nå enn senere. (Vi har ikke utgifter i penger, men vi har i tid)
-   **Once and only once**: Ingen code duplikat. Lage abstraksjoner. 
-   **Refactorer** 
-   **Customer Team Member**
-   **Short Cycles**: Dette er allerede satt av innleveringsfrister.
-   **Pair Programmering**: Fantastisk måte å ha kunnskapsoverføring på, og to hoder tenker bedre enn ett. Men tidsklemma og ulike timeplaner kan gjøre det vanskelig å følge dette slavisk.

### Møter
-   Minst en gang i uken, dette blir da felles gruppetime i gruppe 7.
    -   Mulighet for å organisere dersom det nødvendig

### Kodemøte
-   Kode sammen, og hjelpe hverandre.
-   Følge opp hverandre.
-   Kritisk i begynnelsen, at vi er alle enige om hvordan vi skal skrive koden.

### Kommunikasjon
-   Slack blir vår hovedkanal.

### Arbeidsfordeling
1.  Skriver user stories (Bruker et eget GitHub Project board til dette)
    -   Går igjennom de i fellesskap i møter
    
2.  Definerer arbeidsoppgaver ut i fra user stories

3.  Arbeidsoppgavene fordeles etter interesse og ønske.

### Oppfølging av arbeid
-   Standup før hvert kodemøte
-   Oppfølging via GitHub og GitHub-wiki
-   Via Slack dersom det skulle være spørsmål

### Deling og oppbevaring av felles dokumenter
-   Bruker primært Slack.
-   Bruker også GitHub-wiki til å orgainsere lenker og evt. bilder og diagrammer.

### Kodebase
-   Bruker GitHub til all kode.

## Deloppgave 4
Vi har valgt å lage en egen Project board til, [user stories](https://github.com/inf112-v20/crawling-crow/projects/2). Her tagger vi de ulike arbeidsoppgavene ([work tasks](https://github.com/inf112-v20/crawling-crow/projects/1)) fortløpende. 

## Retrospektiv 
### Prosjektmetodikk

Planlagt å bruke:
-   elementer fra XP nevnt ovenfor.

Ikke vært så flinke til å følge:
-   Vi startet prosjektet i feil ende, derfor ble det vanskelig å følge disse: 
    -   TDD
    -   You ain't gonna need it

### Hvordan gikk prosjektet
Ting som gikk bra:
-   Project Board 2 stk, User-stories, work tasks.
    -   oversiktlig, lett å forstå.
    
-   Wiki, fått nyttige ting på plass. 
    -   alle får oversikt over nyttig info man kan se tilbake til.
    -   retningslinjer, co-author/shortcut
    
-   God kommunikasjon på Slack.
    -   lett å avtale møter, holde alle oppdatert

-   Mye parprogrammering. 
    -   Blitt kjent med hvordan ulike personer jobber. 

Ting som kunne gått bedre: 
-   Komme i gang litt tidligere. 
-   Skulle begynt med det eksisterende biblioteket, ikke fra scratch.
-   Vi hadde problemer med å skrive tester, i forbindelse med assets.

### Til neste gang
-   Lage user-stories og worsk tasks med én gang.
-   Sjekke om det er relevant informasjon/tutorial/bibloteker tilgjengelig.
-   Kanskje dele oss inn i mindre grupper på mindre oppgaver.
-   Vær tydelig på når og hvilke oppgaver man starter på og når økten er ferdig. 

### Konklusjon
-   Fått til det vi skulle, men ikke like organisert som ønsket.

## Klassediagram
![UML](https://user-images.githubusercontent.com/43996726/74036655-f74e3b00-49bc-11ea-8239-1937feec4403.PNG)
