<template>
<div class="teaser spacer stack stack--row stack--justify-space-between">
    <p class="teaser_headline">The largest community of car enthusiasts</p>
    <div class="stack form_login">
        <h1 class="h2">Sign up</h1>
        <label for="email_login">Email address</label>
        <input id="email_login" type="email">
        <label for="password_login">Password</label>
        <input id="password_login" type="password">
        <button @click="login" id="btn_login" class="button button--primary button_login">Login</button>
    </div>
</div>
</template>

<script setup>
import ref from 'vue'

function login(){
    localStorage.setItem('loggedIn',true)
}

</script>