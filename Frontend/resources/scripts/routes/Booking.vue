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
    <p class="error">{{errorMessage}}</p>
    <div :key="i" v-for="i in Math.ceil(cars.length / 3)" class="booking_row stack stack--row stack5">
        <div class="booking_container stack stack3 stack--column" :key="`car_${index}`" v-for="(car,index) in  cars.slice((i-1)*3, i*3)">
            <h2 class="h3">{{ car.make }} - {{ car.model }}</h2>
            <div class="stack8 stack">
            <div class="stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span>Year:</span>
                    <span>{{ car.year }}</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span>Price per day:</span>
                    <div>
                        <span>{{ car.dailyRate }} {{ car.currency }}</span>
                    </div>
                </div>
            </div>
            <div class="booking_summary stack">
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Date</span>
                    <span>{{currentDateFormatted}} - {{endDateFormatted}}</span>
                </div>
                <div class="stack stack--row stack--justify-space-between">
                    <span class="u--text-600">Total price:</span>
                    <div>
                        <span>{{ car.dailyRate * diffDays }} {{ car.currency }}</span>
                    </div>
                </div>
            </div>
        </div>
            <button class="button button--primary" v-bind:value="car.carId" @click="addBooking(car.carId)" type="text">Book</button>
        </div>
    </div>
</div>
</template>

<script setup>
import CarService from "../services/car.service.js"
import BookingService from "../services/booking.service.js"
import store from '../store'
import router from '../router/router.js'
import {ref} from 'vue'

let errorMessage = ref()
let endDate = new Date("2023-12-23")
let endDateFormatted = ref(endDate.toLocaleDateString('en-CA'))
let hour = "10:00:00"
let currentDate = new Date()
let currentDateFormatted = ref(currentDate.toLocaleDateString('en-CA'))
let currentTime = new Date().toLocaleTimeString('en-US', { hour12: false });
let timeDiff = Math.abs(endDate.getTime() - currentDate.getTime());
let diffDays = ref(Math.ceil(timeDiff / (1000 * 3600 * 24))); 

let cars = ref("")
try{
    cars.value = await CarService.getAvailableCars(currentDateFormatted.value, currentTime, endDateFormatted.value, hour, "USD").then(response => {
        return response
    })
    if(cars.value ==""){
        errorMessage.value = "No cars available for the selected parameters."
    }else{
        errorMessage.value =""
    }
}catch{
    errorMessage.value = "Car service isn't available. Please try again!"
}


let userid = store.state.auth.user.userDTO.userId;

async function getCars(){
    let pickUpDate = new Date(document.getElementById('pickUpDate').value)
    let pickUpDateFormatted = pickUpDate.toLocaleDateString("en-CA")
    let pickUpHour = document.getElementById('pickUpTime').value + ":00"
    let returnDate = new Date(document.getElementById('returnDate').value)
    let returnDateFormatted = returnDate.toLocaleDateString("en-CA")
    let returnHour = document.getElementById('returnTime').value +":00"
    let currency = document.getElementById('currency').value
    let timeDiffNew = Math.abs(returnDate.getTime() - pickUpDate.getTime());
    diffDays = Math.ceil(timeDiffNew / (1000 * 3600 * 24))


    if(!isNaN(pickUpDate) && pickUpHour != ":00" && !isNaN(returnDate) && returnHour !=":00" && currency != ""){
        try{
            cars.value = await CarService.getAvailableCars(pickUpDateFormatted, pickUpHour, returnDateFormatted, returnHour, currency).then(response => {
                return response
            })

            if(cars.value ==""){
                errorMessage.value = "No cars available for the selected parameters."
            }else{
                errorMessage.value =""
            }

            currentDateFormatted.value = pickUpDateFormatted
            endDateFormatted.value = returnDateFormatted
            currentTime = pickUpHour
            hour = returnHour

            errorMessage.value = ""
        }catch{
            errorMessage.value = "Car service isn't available. Please try again!"
        }
    }else{

        errorMessage.value = "Please use all input fields."
    }
}

async function addBooking(carid){
    let pickUpDate = currentDateFormatted.value
    let pickUpHour = currentTime
    let returnDate = endDateFormatted.value
    let returnHour = hour

    try {
        await BookingService.addBooking(carid, userid, pickUpDate,pickUpHour,returnDate,returnHour, false).then(response => {
        if (response == 200){
            router.push('/overview')
        }
        errorMessage.value =""
    })
    } catch (error) {
        errorMessage.value ="Booking didn't work. Please try again"
    }

}
</script>
