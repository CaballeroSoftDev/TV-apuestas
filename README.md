# TV-apuestas

Aplicación Android para mostrar información y apuestas deportivas utilizando The Odds API y API-SPORTS.

## Requisitos

- Android Studio
- API Keys para los servicios:
  - [The Odds API](https://the-odds-api.com/)
  - [API-SPORTS](https://api-sports.io/)

## Obtención de las API Keys

1. **The Odds API**  
   Regístrate y obtén tu clave en:  
   [https://the-odds-api.com/](https://the-odds-api.com/)

2. **API-SPORTS**  
   Regístrate y obtén tu clave en:  
   [https://api-sports.io/](https://api-sports.io/)

## Configuración de las API Keys

1. Crea un archivo llamado `local.properties` en la raíz del proyecto (si no existe).
2. Agrega tus claves en el archivo de la siguiente manera:

   ```properties
   ODDS_API_KEY=tu_clave_de_the_odds_api
   API_FOOTBALL_API_KEY=tu_clave_de_api_sports
   ```

## ¿Cómo se utilizan las claves?

El archivo `app/build.gradle.kts` carga las claves del archivo `local.properties` y las expone a tu código como variables de configuración `BuildConfig.ODDS_API_KEY` y `BuildConfig.ALL_SPORTS_API_KEY`.
