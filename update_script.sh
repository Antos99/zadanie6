curl -X 'PUT' \
  'http://localhost:8080/api/v1/communications/' \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  -F 'bodyFile=@'$1';type=text/plain' \
  -F 'deliverySettingsFile=@'$2';type=application/json' \
  -F 'updateData={"id":'$3',"name":"'$4'"};type=application/json'
