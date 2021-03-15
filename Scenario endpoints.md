### Sign In

    # curl localhost:8080/signin
      -X POST
      -H 'Content-Type:application/json' 
      -d '{"username":"test","password":"test"}'

    POST localhost:8080/signin
    Content-Type: application/json

    {"username":"test","password":"test"}



### Sign Up

    # curl localhost:8080/signup 
      -X POST 
      -H 'Content-Type:application/json' 
      -d '{"username":"test","password":"test"}'

    POST localhost:8080/signup
    Content-Type: application/json

    {"username":"test","password":"test"}



### Create an URL alias (with random generated alias name)

    POST localhost:8080/urls/shorten
    curl localhost:8080/urls/shorten 
      -X POST 
      -H 'Content-Type:application/json' 
      -H 'Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>’ 
      -d '{"url":"https://www.google.com"}'
    Content-Type: application/json
    Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>

    {"url":"https://www.google.com/"}



### Create an URL alias (with own alias name)

    # curl localhost:8080/urls/shorten 
      -X POST 
      -H 'Content-Type:application/json' 
      -H 'Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>’ 
      -d '{"url":"https://gist.new", "alias":"gist"}'



### List user’s shortened links

    # curl localhost:8080/urls/sh 
      -X GET
      -H 'Content-Type:application/json'
      -H ‘Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>’
    #/shorten
    Content-Type: application/json
    Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>



### Redirect by a shortened URL

    GET localhost:8080/r/gist
    #/shorten



### Delete shortened link

    DELETE localhost:8080/urls/gist
    #/shorten
    Content-Type: application/json
    Authorization: Bearer <TOKEN FROM THE LOGIN RESPONSE>
