<template>
<div class="spacer stack">
    <h1>Map</h1>
    <div class="stack stack--row">
    <label for="start">Start:</label>
      <input id="start">
      <label for="end">End:</label>
      <input id="end">
    </div>
    <div id="container stack">
      <div id="map"></div>
      <div id="sidebar"></div>
    </div>

</div>

</template>


<script setup>
let map

 map = document.createElement('script')
      map.setAttribute('src', '')
      document.body.appendChild(map)

function initMap() {
  const directionsRenderer = new google.maps.DirectionsRenderer();
  const directionsService = new google.maps.DirectionsService();
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 3,
    center: { lat: 41.85, lng: -87.65 },
    
  });

  directionsRenderer.setMap(map);
  directionsRenderer.setPanel(document.getElementById("sidebar"));

  const control = document.getElementById("floating-panel");

  map.controls[google.maps.ControlPosition.TOP_CENTER].push(control);

  const onChangeHandler = function () {
    calculateAndDisplayRoute(directionsService, directionsRenderer);
  };

  document
    .getElementById("start")
    .addEventListener("change", onChangeHandler);
  document
    .getElementById("end")
    .addEventListener("change", onChangeHandler);
}

function calculateAndDisplayRoute(directionsService, directionsRenderer) {
  const start = document.getElementById("start").value;
  const end = document.getElementById("end").value;
  directionsService
    .route({
      origin: start,
      destination: end,
      travelMode: google.maps.TravelMode["DRIVING"],
    })
    .then((response) => {
      directionsRenderer.setDirections(response);
    })
}

window.initMap = initMap;
</script>
