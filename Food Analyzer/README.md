🍔 Food Analyzer is a server-client application which informs clients about nutrient facts of a particular food.
    
    I. Work of server:
      
      1. To accept multiple requests from clients in the same time.
      
      2. To analyze request, to check whether they are valid and to retrieve barcode number from an image if it is necessary.
      
      3. To send requests, containing the information from the clients' requests, to RESTful Api in the form of http requests.
      
      4. To accept replies, which are in json format, from the api and to transform the information in oop format.
      
      5. To save information from http replies in order to get it quick if there are the same client requests in the future.
      
      6. To save information from hhtp replies also in a file.
      
      7. To send replies to clients. 
      
   
    II. Work of client:
     
      1. To send requests to server.
      
      2. To accept replies from server.
      
      3. To print replies on the terminal.
      
      4. To disconnect if a client wants to.
      
   
    III. Valid client food requests:
      
       1. get-food valid-food-name - searching for information about food by name.
      
       2. get-food-report valid-food-id - searching for information about food by food id. 
      
       3. get-food-by-barcode valid-barcode-number / image-containing-valid-barcode - searching for food information by barcode
          in the already saved information from previous requests because the RESTful api does not support this functionality.
       
   
    IV. Responses from server:
      
       1. get-food returns: name of food, description, food id, barcode, food content.
      
       2. get-food-report returns: name of food, food facts such as: calories, fats, sugars, carbohydrates, etc.
      
       3. get-food-by-barcode returns: the same as get-food command.
      
       4. if the command is not valid: "Food request is not valid!".
     
       5. if the food cannot be found due to invalid arguments or if it is not already saved before searching by barcode
          or the RESTful api does not consist information about the food: "Food is not found!". 
       
   
    V. Instructions how to run Food Analyzer.
     
      1. Get a valid api key from: https://fdc.nal.usda.gov/api-key-signup.html
      
      2. Put your api key in the place of <API KEY> in FoodAnalyzerHttpClient.
      
      1. Allow parallel running of the program, if it is necessary.
      
      2. Run FoodAnalyzerNioServer
      
      3. Run FoodAnalyzerNioClient as many times as you want.
      
      4. Write a command (from III.) on the client side. 
      
      5. If you want to search for food information by barcode, make sure you have already made a get-food request
         of the same food in order to save the food information and if you search by image, it should contain only the barcode.
      
      6. Write disconnect if you want to quit a client.
