# reset-and-run.ps1
# Vide le schema PostgreSQL puis relance l'ETL

Write-Host "==> Remise a zero de la base de donnees..."
docker exec java-dp-postgres psql -U postgres -d TON_DB -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public; GRANT ALL ON SCHEMA public TO postgres;"

Write-Host "==> Lancement de l'ETL..."
mvn compile exec:java
