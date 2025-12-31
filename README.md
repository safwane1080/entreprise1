# Art Equipment Rental EHB
## Projectbeschrijving

Dit project is een **webapplicatie ontwikkeld in Java (Spring Boot)** als proof of concept voor een kunstopleiding.  
Het doel van de applicatie is om **studenten materiaal te laten reserveren en huren** voor artistieke projecten en eindwerken.

Het materiaal bestaat onder andere uit:
- Belichting (lampen, LED-panelen)
- Audio (microfoons, mixers)
- Podium- en riggingmateriaal
- Kabels en accessoires

De applicatie bevat een **beperkte maar representatieve catalogus** (±10 producten) om de kernfunctionaliteit te demonstreren.

---

## Functionaliteiten

### Catalogus, filtering en sortering
- Overzicht van alle producten
- Filteren op categorie (belichting, audio, kabels, podium)
- Zoekfunctie op naam en beschrijving
- Sorteren van producten:
  - Alfabetisch (A–Z)
  - Alfabetisch (Z–A)
  - Prijs (laag → hoog)
  - Prijs (hoog → laag)

Sortering gebeurt **server-side via Spring Data JPA** en kan gecombineerd worden met zoeken en filters.

---

### Winkelmand en sessiebeheer
- Producten toevoegen aan een winkelmand
- Winkelmand gekoppeld aan de ingelogde gebruiker
- Instelbaar:
  - Aantal items
  - Aantal huurdagen per product
- Automatische berekening van subtotalen en totaalprijs
- Winkelmand blijft behouden binnen de gebruikerssessie

---

### Checkout en reservatieperiode
- Startdatum kiezen bij checkout
- Einddatum wordt automatisch berekend op basis van de **langste huurperiode**
- Bevestigingspagina na succesvolle reservatie
- Reservaties blijven gekoppeld aan de gebruiker

---

### Login en registratie
- Registratiesysteem voor nieuwe gebruikers
- Login via e-mail en wachtwoord
- Wachtwoorden worden veilig opgeslagen met **BCrypt hashing**

---

### Gebruikersprofiel en accountbeheer

Elke gebruiker beschikt over een profielpagina.

**Profielgegevens**
- Voornaam en achternaam aanpassen
- Gegevens worden veilig opgeslagen in de database

**Wachtwoord wijzigen**
- Huidig wachtwoord moet correct zijn
- Nieuw wachtwoord moet bevestigd worden
- Validatie op wachtwoordsterkte:
  - Minstens 8 tekens
  - Minstens één hoofdletter
  - Minstens één kleine letter
  - Minstens één cijfer
- Wachtwoorden worden opnieuw opgeslagen met **BCrypt**
- Duidelijke foutmeldingen bij ongeldige invoer

Alle validaties gebeuren **server-side**.

---

### Beveiliging
- Spring Security
- Beveiligde routes:
  - Winkelmand
  - Checkout
  - Reservaties
  - Profiel
- Publieke routes:
  - Home
  - Login
  - Registratie
  - Catalogus

---

## Technische stack

- **Java 17**
- **Spring Boot**
- Spring MVC
- Spring Security
- Spring Data JPA (Hibernate)
- Thymeleaf
- H2 Database (in-memory)
- HTML5 / CSS3

---

## Projectstructuur

src/
└── main/
├── java/
│ └── be/entreprise/entreprise1/
│ ├── controller/
│ ├── model/
│ ├── repository/
│ └── service/
└── resources/
├── templates/
├── static/css/
└── application.properties


---

## Designkeuzes

- Duidelijke scheiding tussen controller-, service- en repository-laag
- Businesslogica in de service-laag (bv. checkout)
- Entity-relaties via JPA (User → Order → CartItem)
- Einddatum van een reservatie wordt bepaald door de **langste huurperiode**
- Server-side filtering en sortering voor veiligheid en consistentie

---

## Security-keuzes

- **BCryptPasswordEncoder** voor veilige wachtwoordopslag
- Custom loginpagina
- Server-side validatie voor gevoelige acties
- CSRF uitgeschakeld voor eenvoud (proof of concept)
- Sessies beheerd door Spring Security

---

## Gebruik van AI

Tijdens de ontwikkeling werd **ChatGPT** gebruikt als ondersteunend hulpmiddel voor:
- Debugging
- Structureren van controllers en services
- Uitleg en validatie van security-oplossingen
- Verbetering van UI-structuur

Alle gegenereerde code werd **begrepen, aangepast en geïntegreerd** door de student zelf.  
De werking van het project kan mondeling volledig worden toegelicht.

---

## Referenties

- Spring Boot Documentation  
  https://spring.io/projects/spring-boot

- Spring Security Documentation  
  https://spring.io/projects/spring-security

- Thymeleaf Documentation  
  https://www.thymeleaf.org/documentation.html

- Baeldung – Spring Tutorials  
  https://www.baeldung.com/

---

## Project uitvoeren

1. Clone of download het project
2. Open het project in IntelliJ IDEA
3. Start `Entreprise1Application.java`
4. Ga naar `http://localhost:8080`

---

## Demo

Het project werd getest met:
- Meerdere gebruikers
- Volledige reservatieflow (catalogus → winkelmand → checkout → bevestiging)
- Combinatie van zoeken, filteren en sorteren
- Beveiligde routes en profielbeheer

---

## Auteur

**Safwane El Masaoudi**  
Bachelor Toegepaste Informatica  
Erasmushogeschool Brussel


