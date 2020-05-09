# Obligatorisk oppgave 4
## Deloppgave 1: Team og prosjekt
- Se vår wiki, for [kodemøter](/../../wiki/Kodemøter) og [møter](/../../wiki/Møtereferater) ellers.
- Alt fungerer bra, utenom vi trenger å kommunisere litt klarere med hva vi jobber med. Trenger heller ikke å oppdatere rollene. Vi er fornøyd sånn som de er.

- Med tanke på prosjektplanlegging, så burde vi ha satt oss ned i begynnelsen og blitt enige om implemtasjonen av de ulike reglene i RoboRally. I begynnelsen møttes vi bare i de obligatoriske gruppetimene utenom innleveringsuken (skippertakmetodikk).
- Team-messig burde vi fått til mer par-programmering for å skjønne hvordan vi alle tenker og koder, vi satset litt mye i begynnelsen på at alle kunne bidra med en del også ville vi enkelt kunne flette dette sammen. Dette gikk ikke slik som vi hadde tenkt, men vi løste dette delvis ved å par-programmere.
    - Vi fant også ut at vi tenker veldig forskjellig, og da ble kodingen og kodestilen vanskelig å flette sammen.


### Retrospektiv
#### Hva gjorde vi bra?
- Satt opp møter og hatt jevn kommunikasjon
- Hjulpet hverandre hvis noen har spurt
- Jevn forbedring av jobbing i prosjekt
- Jevn forbedring av tydlighet av hva koden gjør
- Jevn forbedring i bruk av github og git
- Fulgt planen som vi har satt opp for hver oblig

#### Hva vi kunne gjort annerledes?
- Balansere hvor mye som skal planlegges
    - Gått mer nøye gjennom regelboken i fellesskap
- Sette seg inn i rammeverk/bibliotek og undersøke funksjonalitet tidligere/fra begynnelsen av
- Legge mer vekt på felles forståelse av kodestil/definere kodestil
- Brukt branching mer effektivt og være flinkere til å branche ut når: 
    - Vi oppretter nye elementer av spillet
    - Vi løser issues


### Starten av prosjeketet
#### Hvordan fungerer gruppedynamikken og kommunikasjonen nå i forhold til i starten?
- Kommunikasjonen har blitt mye bedre underveis, men vi føler alltid kunne kommunisert bedre.
- Vi har blitt flinkere å spørre, om både ting og hjelp når vi lurer på noe.
    - Flinkere å ta opp ting som må diskuteres i plenum, eks. fremgangsmåter osv.
- Kodestilen utviklet seg positivt, etterhvert som vi møttes jevnlig og par-/gruppe-programmerte og ble lettere å forstå hverandre. Vi gikk gjennom mye kode og refaktorerte sammen, for å få en felles forståelse for koden.


### Hvordan påvirket karantene og nedstengning teamet og fremdriften?
- Det har vært lettere å par-programmere med tanke på at Zoom har vært et bra og viktig verktøy for oss.
- Det har vært lettere å finne møtetidspunkter.
    - Dette er også pga vi var ferdig med forelesninger, da brukte vi disse tidene som gruppe- og kodemøter.
- Vi har sett hvor viktig det er å være oppdatert på GitHub både i prosjekttavlene, generelle issues og hvilke commits som har blitt gjort i de ulike branch'ene.
- For prosjektet sin har heldigitaliseringen bare vært positivt.

### Skjermdump av projectboard
#### Arbeidsoppgaver
![Skjermbilde 2020-05-08 kl  15 29 14](https://user-images.githubusercontent.com/5453010/81410242-b4793c00-9140-11ea-9d0b-1176751d9749.png)

#### User stories
![Skjermbilde 2020-05-08 kl  15 30 21](https://user-images.githubusercontent.com/5453010/81410340-d5da2800-9140-11ea-9742-cc77e944d44a.png)



## Deloppgave 2: Krav
### Prioriterte krav
- Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrigegang.
    - Hva som egentlig har blitt ferdig og hvorfor.
- Vi har fullførte hele MVP
- Vi valgte å lage AI istedenfor nettverk
- Vi valgte å gjøre noen "nice to have":
    - Leaderboard: For å ha oversikt over hvordan man ligger ann i spillet
    - Flere brett og valg av brett: For variasjon
    - Archive marker: For å kunne ha en oversikt over hvor robotene, inkludert deg selv, "spawner".
    - Fyrverkeri når noen vinner: For moro skyld 
    - Robot eksploderer når den dør: For tydelighet når en robot dør
    - Bakgrunnsbilde: For moro skyld
    - Velge navn på din robot: Personliggjør robot og ser lettere hvilken som er din
    - Animasjon når en robot faller i et hull: For moro skyld 
    - Teksturendring når robot går på flagg: For tydelighet når en robot tar et flagg
    - Endre på instillinger; musikk, volum, spillhastighet, laserhastighet: For debugging og for moro skyld
    - Lydeffekter: For moro skyld
    - Laseranimasjon: For klarhet og kunne se når du blir skutt.
    
- Vi har gjort siden forrige gang:
    - Power down #114
    - User interface #116
    - Conveyorbelt #109
    - Rotating cogs #110
    - Death #112
    - Repair #111
    - Rounds, phases, steps #108
    - AI

- Se [user stories](https://github.com/inf112-v20/crawling-crow/projects/2)

- Dette er hva vi anser som [MVP](https://github.com/inf112-v20/crawling-crow/wiki/Spillkrav) siden dette er nødvendig spillmekanikk fra selve brettspillet.

- For våre bugs, se [known bugs](../README.md#known-bugs) i vår [README](../README.md), ev. vår issue-label [bug](https://github.com/inf112-v20/crawling-crow/issues?q=is%3Aissue+is%3Aopen+label%3Abug+sort%3Aupdated-desc).


## Deloppgave 3: Produktleveranse og kodekvalitet
- Dokumentasjon på hvordan man bygger, tester og kjører vår versjon av Robo Rally ligger i vår [README](../README.md#setup) .
- Grunnen til at det er forskjeller på hvem som bidrar til kodebasen er pga vi har gjort mye par-programmering og brukt [co-authored-by](https://github.com/inf112-v20/crawling-crow/wiki#co-authored-by)

### Prosjektpresentasjon
- Se [Prosjektpresentasjon.md](Prosjektpresentasjon.md)


### Klassediagram
Ligger oppdatert i [README](../README.md#class-diagram) og her:

![klassediagram-oblig4](https://user-images.githubusercontent.com/59846048/81407446-b391db80-913b-11ea-8244-0b5d6b1d3707.png)

