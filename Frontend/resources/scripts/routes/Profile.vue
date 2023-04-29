<template>
<div class="spacer">
    <div class="profile stack stack5">
    <h1>My Profile</h1>
    <div class="stack stack5 stack--row">
        <h2 class="h6">E-Mail:</h2>
        <div>{{user.email}}</div>
        <div>        
            <label for="edit_email">New email</label>
            <input id="edit_email" type="email">
        </div>
        <button class="button button--primary" @click="changeEmail()">Edit</button>
    </div>
    <div class="stack stack5 stack--row">
        <h2 class="h6">Password:</h2>
        <div>***</div>
        <div>
            <label for="edit_password">New password</label>
            <input id="edit_password" type="password">
        </div>
        <div>
            <label for="edit_repeat">Repeat password</label>
            <input id="edit_repeatPassword" type="password">
        </div>
        <button class="button button--primary" @click="changePassword()">Edit</button>
    </div>
    <button class="button button--delete" @click="deleteUser()">Delete account</button>
</div>
</div>
</template>

<script setup>
import {ref} from 'vue'
import userService from '../services/user.service'

let user = ref(JSON.parse(localStorage.getItem('user')))

async function changePassword(){
    let newPassword = document.getElementById('edit_password')
    return await userService.changePassword(userid, newPassword).then(response => {return response})
}

async function changeEmail(){
    let newEmail = document.getElementById('edit_email').value
    return await userService.changeEmail(user.value, newEmail).then(response => user.value = JSON.parse(localStorage.getItem('user')))
}

async function deleteUser(){
    return await userService.delete(user.value).then(response => userService.logout)
}


</script>