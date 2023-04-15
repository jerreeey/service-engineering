<template>
<div class="teaser spacer stack stack--row stack--justify-space-between">
    <p class="teaser_headline">The largest community of car enthusiasts</p>
    <div class="stack form_login">
        <h1 class="h2">Sign up</h1>
        <label for="email_login">Email address</label>
        <input v-model="user.email" name="email" id="email_login" type="email">
        <label for="password_login">Password</label>
        <input v-model="user.password" id="password_login" type="text" name='password'>
        <button @click="login" id="btn_login" class="button button--primary button_login">Login</button>
    </div>
</div>
</template>

<script setup>
import { ref ,computed, created, reactive } from 'vue'
import User from '../models/user';
import store from '../store'
import router from '../router/router.js'

const user = reactive(new User('',''))
const loading = ref(false)  
const message = ref('')
const loggedIn = computed(() => {
    return store.state.auth.status.loggedIn
})                    
console.log(user.email)


function login(){
        if (user.email && user.password) {
            store.dispatch('auth/login', user).then(
                () => {
                    console.log(store.state.auth.user)
                    router.push('/');
                },
                error => {
                    this.message =
                        (error.response && error.response.data) ||
                        error.message ||
                        error.toString();
                }
            );
        }
}

</script>