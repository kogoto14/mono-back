# mono-back

## ローカル起動方法
プロジェクトルートで以下を実行
```
./mvnw clean package -DskipTests
docker compose --env-file .env.dev build --no-cache app
docker compose --env-file .env.dev up -d
```
