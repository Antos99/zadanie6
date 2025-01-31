# Run app
Docker required
```shell
./run.sh
```

# Swagger
Swagger available at: http://localhost:8080/swagger-ui/index.html

Warning: for update requests please use update_script.sh due to swagger bug: https://github.com/swagger-api/swagger-ui/issues/4826
Example call:
```shell
./update_script.sh example_files/komunikacja_3.txt example_files/komunikacja_3.json 1 new_name
```

# Database
Database connection details:

URL: jdbc:postgresql://localhost:5432/postgres<br/>
username: postgres<br/>
password: mypassword<br/>

# Monitoring catalog
Application monitors catalog: ./monitoring_catalog. This can be changed in compose.yaml file
