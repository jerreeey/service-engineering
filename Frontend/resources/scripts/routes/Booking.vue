<template>
<div class="spacer">
    <div class="stack stack--justify-space-between stack5">
    <h1>Book car</h1>
    <div class="container searchbar stack stack--row stack--justify-space-between">
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
            <label class="stack">Currency
                <select id="currency">
                    <option>USD</option>
                    <option>JPY</option>
                    <option>BGN</option>
                    <option>CZK</option>
                    <option>DKK</option>
                    <option>EUR</option>
                    <option>GBP</option>
                    <option>HUF</option>
                    <option>PLN</option>
                    <option>RON</option>
                    <option>SEK</option>
                    <option>CHF</option>
                    <option>ISK</option>
                    <option>NOK</option>
                    <option>TRY</option>
                    <option>BRL</option>
                    <option>CAD</option>
                    <option>CNY</option>
                    <option>HKD</option>
                    <option>ILS</option>
                    <option>INR</option>
                    <option>KRW</option>
                    <option>MXN</option>
                    <option>MYR</option>
                    <option>NZD</option>
                    <option>PHP</option>
                    <option>SGD</option>
                    <option>THB</option>
                    <option>ZAR</option>
                </select>
            </label>
        </div>
        <button class="button button--primary"  @click="getCars">Search</button>
    </div>
    </div>
    <div :key="i" v-for="i in Math.ceil(cars.length / 3)" class="booking_row stack stack--row stack5">
        <div class="booking_container stack stack3 stack--column" :key="`car_${index}`" v-for="(car,index) in  cars.slice((i-1)*3, i*3)">
            <h2 class="h3">{{ car.name }} - {{ car.model }}</h2>
            <div class="stack8 stack">
            <div class="stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span>Year:</span>
                    <span>{{ car.year }}</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span>Price per day:</span>
                    <div>
                        <span>{{ car.pricePerDay }} {{ car.currency }}</span>
                    </div>
                </div>
            </div>
            <div class="booking_summary stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Date</span>
                    <span>28.03.2023 - 31.03.2023</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Total price:</span>
                    <div>
                        <span>{{ car.totalPrice }} {{ car.currency }}</span>
                    </div>
                </div>
            </div>
        </div>
            <button class="button button--primary" v-bind:value="car.id" @click="addBooking(car.id, car.pickUpDate, car.pickUpHour, car.returnDate, car.returnHour)" type="text">Book</button>
        </div>
    </div>
</div>
</template>

<script setup>
import CarService from "../services/car.service.js"
import BookingService from "../services/booking.service.js"
import {ref} from 'vue'

var options = { day: '2-digit', year: 'numeric', month: '2-digit' };
let date = new Date().toLocaleDateString("de-DE", options)
let endDate = "23.12.2023"
let hour = "10:00"

let cars = ref(await CarService.getAvailableCars(date, hour, endDate, hour, "USD").then(response => {
    return response
})
)

let userid = JSON.parse(localStorage.getItem('user')).id;

async function getCars(){
    let pickUpDate = new Date(document.getElementById('pickUpDate').value).toLocaleDateString("de-DE", options)
    let pickUpTime = document.getElementById('pickUpTime').value
    let returnDate = new Date(document.getElementById('returnDate').value).toLocaleDateString("de-DE", options)
    let returnTime = document.getElementById('returnTime').value
    let currency = document.getElementById('currency').value

    cars.value = await CarService.getAvailableCars(pickUpDate, pickUpTime, returnDate, returnTime, currency).then(response => {
        return response
    })
}

async function addBooking(carid, pickUpDate,pickUpHour,returnDate,returnHour){
    await BookingService.addBooking(carid, userid, pickUpDate,pickUpHour,returnDate,returnHour).then(response => {
        return response
    })
}
</script>
