<template>
<div class="spacer">
    <div class="stack stack--justify-space-between stack5">
    <h1>Book car</h1>
    <div class="searchbar stack stack--row stack--justify-space-between">
    <div class=" stack stack--row stack--justify-space-between">
        <label class="stack" for="pickUpDate">Pick up date
            <input id="pickUpDate" type="date">
        </label>
        <label class="stack">Pick up time
            <input id="pickUpTime" type="time">
        </label>
        <label class="stack">Return date
        <input id="returnDate" type="date">
        </label>
        <label class="stack">Return time
        <input id="returnTime" type="time">
        </label>
    </div>
        <button class="button button--primary" type="text">Search</button>
    </div>
    <ul>
        <li :key="`car_${index}`" v-for="(car,index) in cars">
         {{ car.name }}
        </li>
    </ul>
    </div>
</div>

</template>

<script setup>
import {ref} from "vue"
const cars = ref([
    {
        name: "VW",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Ferrari",
        model: "Golf",
        price: 20,
        year: "2010",

    },
    {
        name: "Toyota",
        model: "Golf",
        price: 20,
        year: "2010",

    }
])
</script>
