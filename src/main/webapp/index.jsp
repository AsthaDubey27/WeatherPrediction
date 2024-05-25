<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather App</title>
    <link rel="stylesheet" href="style.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    <style>
    #recommendationButton {
    background-color: #4CAF50; /* Green background */
    border: none;
    padding: 10px 20px; /* Padding */
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 8px; /* Rounded corners */
    transition-duration: 0.4s;
}

#recommendationButton:hover {
    background-color: #45a049; /* Darker green background on hover */
}
    </style>
</head>
 <script>
        function showRecommendation() {
            var recommendation = document.getElementById("recommendation").value;
            document.getElementById("recommendationContainer").innerText = recommendation;
            var mainContainer = document.querySelector('.mainContainer');
            mainContainer.style.padding = '30px'; // Adjust the padding value as needed

        }
    </script>
<body>
<div class="mainContainer">
    <form action="MyServlet" method="post" class="searchInput">
        <input type="text" placeholder="Enter City Name" id="searchInput" value="Current Weather" name="city"/>
        <button id="searchButton"><i class="fa-solid fa-magnifying-glass"></i></button>
    </form>
    <div class="weatherDetails">
        <div class="weatherIcon">
            <img src="Images/clouds.png" alt="Clouds" id="weather-icon">
            <h2>${temperature} °C</h2>
            <input type="hidden" id="wc" value="${weatherCondition}">
        </div>
        
        <div class="cityDetails">        
            <div class="desc"><strong>${city}</strong></div>
            <div class="date">${date}</div>
        </div>
        <div class="windDetails">
            <div class="humidityBox">
                <img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhgr7XehXJkOPXbZr8xL42sZEFYlS-1fQcvUMsS2HrrV8pcj3GDFaYmYmeb3vXfMrjGXpViEDVfvLcqI7pJ03pKb_9ldQm-Cj9SlGW2Op8rxArgIhlD6oSLGQQKH9IqH1urPpQ4EAMCs3KOwbzLu57FDKv01PioBJBdR6pqlaxZTJr3HwxOUlFhC9EFyw/s320/thermometer.png" alt="Humidity">
                <div class="humidity">
                    <span>Humidity </span>
                    <h2>${humidity}% </h2>
                </div>
            </div> 
            <div class="windSpeed">
                <img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiyaIguDPkbBMnUDQkGp3wLRj_kvd_GIQ4RHQar7a32mUGtwg3wHLIe0ejKqryX8dnJu-gqU6CBnDo47O7BlzCMCwRbB7u0Pj0CbtGwtyhd8Y8cgEMaSuZKrw5-62etXwo7UoY509umLmndsRmEqqO0FKocqTqjzHvJFC2AEEYjUax9tc1JMWxIWAQR4g/s320/wind.png">
                <div class="wind">
                    <span>Wind Speed</span>
                    <h2> ${windSpeed} km/h</h2>
                </div>
            </div>
        </div>
    </div>
    
    <h2>Recommendation</h2>
    <button  id="recommendationButton" onclick="showRecommendation()">Get Recommendation</button>
    <input type="hidden" id="recommendation" value="${recommendation}">
    <p id="recommendationContainer"></p>
</div>

</body>
</html>
