# Technische documentatie – Art Equipment Rental EHB

## 1. Architectuur-overzicht

Dit project volgt een klassieke **Spring Boot MVC-architectuur**:

- **Controller-laag**
    - Verantwoordelijk voor routing, inputvalidatie en view-resolutie (Thymeleaf)
- **Service-laag**
    - Bevat alle businesslogica (bv. checkout, periodeberekening)
- **Repository-laag**
    - Verantwoordelijk voor database-interactie via Spring Data JPA
- **Model-laag**
    - JPA-entiteiten die de domeinstructuur voorstellen

Deze opsplitsing zorgt voor:
- Betere leesbaarheid
- Herbruikbare businesslogica
- Eenvoudiger onderhoud en testing

---

## 2. Checkout & periodeberekening

### Probleemstelling
Bij een reservatie kan een gebruiker **meerdere producten tegelijk huren**, elk met een eigen aantal huurdagen.  
De applicatie moet bepalen **tot wanneer de volledige reservatie loopt**.

### Oplossing
De **langste huurperiode** bepaalt de einddatum van de volledige reservatie.

### Implementatie (CheckoutService)

- De gebruiker kiest één **startdatum**
- Alle CartItems van de gebruiker worden opgehaald
- De maximale huurduur (`maxDays`) wordt bepaald
- De einddatum wordt berekend als:

endDate = startDate + maxDays


### Motivatie
- In de praktijk wordt materiaal in één keer opgehaald en teruggebracht
- De gebruiker hoeft geen verschillende einddatums te beheren
- De oplossing is logisch, realistisch en gebruiksvriendelijk

---

## 3. Sessies & winkelmand

- De winkelmand is **gekoppeld aan de ingelogde gebruiker**
- CartItems worden tijdelijk opgeslagen tot checkout
- Bij checkout:
    - Wordt een Order aangemaakt
    - Worden CartItems gekoppeld aan die Order
    - Verdwijnt de winkelmand impliciet (items zijn niet meer “los”)

Hiermee voldoet het project aan:
> “Student kan een gebruikerssessie bijhouden, koppelt hieraan een winkelmandje en raakt naar een checkoutscherm of bevestiging.”

---

## 4. Security & authenticatie

### Authenticatie
- Login via **e-mail + wachtwoord**
- Wachtwoorden worden gehashed met **BCrypt**

### Autorisatie
- Publieke pagina’s:
    - Catalogus
    - Login
    - Registratie
- Beveiligde pagina’s:
    - Winkelmand
    - Checkout
    - Profiel
    - Reservaties

Spring Security beheert:
- Sessies
- Login flow
- Routebescherming

CSRF is bewust uitgeschakeld wegens **proof of concept** context.

---

## 5. Profielbeheer

Gebruikers kunnen via hun profiel:
- Voornaam en achternaam aanpassen
- Hun wachtwoord wijzigen

Bij wachtwoordwijziging:
- Huidig wachtwoord wordt gecontroleerd
- Nieuwe wachtwoorden moeten voldoen aan:
    - Minstens 8 tekens
    - Hoofdletter
    - Kleine letter
    - Cijfer

Fouten worden afgehandeld via redirects met parameters en duidelijke meldingen in de UI.

---

## 6. Designkeuzes (verantwoording)

- **Service-laag** voorkomt dat businesslogica in controllers terechtkomt
- **Thymeleaf** werd gekozen wegens goede integratie met Spring
- **H2 database** is geschikt voor snelle testing en demo’s
- **Spring Data JPA** vereenvoudigt database-interactie zonder SQL-boilerplate

Alle keuzes zijn gemaakt met focus op:
- Duidelijkheid
- Onderhoudbaarheid
- Proof-of-concept scope

