# TV-apuestas

Aplicación Android para mostrar información y apuestas deportivas utilizando The Odds API y AllSportsAPI.

## Requisitos

- Android Studio
- API Keys para los servicios:
  - [The Odds API](https://the-odds-api.com/)
  - [AllSportsAPI Soccer/Football](https://allsportsapi.com/soccer-football-api)

## Obtención de las API Keys

1. **The Odds API**  
   Regístrate y obtén tu clave en:  
   [https://the-odds-api.com/](https://the-odds-api.com/)

2. **AllSportsAPI Soccer/Football**  
   Regístrate y obtén tu clave en:  
   [https://allsportsapi.com/soccer-football-api](https://allsportsapi.com/soccer-football-api)

## Configuración de las API Keys

1. Crea un archivo llamado `local.properties` en la raíz del proyecto (si no existe).
2. Agrega tus claves en el archivo de la siguiente manera:

   ```properties
   ODDS_API_KEY=tu_clave_de_the_odds_api
   ALL_SPORTS_API_KEY=tu_clave_de_allsportsapi
   ```

## ¿Cómo se utilizan las claves?

El archivo `app/build.gradle.kts` carga las claves del archivo `local.properties` y las expone a tu código como variables de configuración `BuildConfig.ODDS_API_KEY` y `BuildConfig.ALL_SPORTS_API_KEY`.
