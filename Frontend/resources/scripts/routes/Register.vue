<template>
    <div class="teaser spacer stack stack--row stack--justify-space-between">
        <p class="teaser_headline">The largest community of car enthusiasts</p>
        <div class="stack form_login">
            <h1 class="h2">Sign up</h1>
            <label for="email_register">Email address</label>
            <input id="email_register" type="email">
            <label for="password_register">Password</label>
            <input id="password_register" type="password">
            <label for="password_register-repeat">Repeat Password</label>
            <input id="password_register-repeat" type="password">
            <button id="btn_register"  @click="register" class="button button--primary button_login">Register</button>
            <p class="error">{{errorMessage}}</p>
        </div>
    </div>
</template>

<script setup>
import { ref ,computed, created, reactive } from 'vue'
import User from '../models/user';
import store from '../store'
import router from '../router/router.js'
let errorMessage = ref("")
const user = new User('','')
function register(){
    let email = document.getElementById('email_register').value
    let password = document.getElementById('password_register').value
    let repeatedPassword = document.getElementById('password_register-repeat').value
    
    if (email != "" && password != "" &&  repeatedPassword != "" && password == repeatedPassword) {
        user.email = email
        user.password = password
        try{
            store.dispatch('auth/register', user).then(
                () => {
                    errorMessage.value = ""
                    router.push('/login');
                },
                error => {
                    errorMessage.value = "Register didn't work"
                }
            );
        }catch{
            errorMessage.value = "Register didn't work"
        }

    } else {
        errorMessage.value = "Form is not filled correctly"
    }

}


</script>