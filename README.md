# Karate Students Tracking App
Ez az alkalmazás karate edzésre járó diákok nyilvántartására szolgál. Az alkalmazás Postgresql adatbázisban tartja nyilván a diákok alapvető adatait,
illetve azt hogy melyik edzésekre járnak a nyilvántartásban szereplőek közül, és ezeket az edzéseket melyik edző tartja.

## Az alkalmazás funkciói
- Alapvető CRUD műveletek a diákokon, edzőkön, edzéseken
- Diákok hozzárendelése ID alapján edzésekhez
- Edzők hozzárendelése ID alapján edzésekhez

## Futtatás
Az alkalmazás futtatható: 
  1. A terminálba írt *docker compose up* paranccsal
  2. A *start_psql_container.sh* script (ami létrehozza psql docker containert) futtatása után a KaratestudentsApplication futtatásával

## Swagger
Miután az alkalmazás elindult az alábbi módokon lehet kéréseket küldeni felé böngészőből:

### Docker compose up indítás
- Miután *docker compose up* paranccsal indítottuk el az alkalmazást az [alábbi linken](http://localhost/swagger-ui/index.html) érjük el a swagger oldalt ahol kéréseket küldhetünk.
- Ekkor ügyeljünk rá hogy a servers lenyíló menüben a Docker-t válasszuk ki mielőtt kérést küldünk

### Alkalmazás indítása IDE-ből
- A második számú indítás elvégzése után az [alábbi linkre](http://localhost:8080/swagger-ui/index.html) kattintva tudunk kérést küldeni ugyanúgy a swaggeren keresztül.
- Ekkor a lenyíló menüben a Local server-t válasszuk kérés küldése előtt

