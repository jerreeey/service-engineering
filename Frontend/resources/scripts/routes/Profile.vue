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
<p class="error">{{errorMessage}}</p>
</div>
</template>

<script setup>
import {ref} from 'vue'
import userService from '../services/user.service'
let user = ref(JSON.parse(localStorage.getItem('user')).userDTO)
let errorMessage = ref()
async function changePassword(){
    let newPassword = document.getElementById('edit_password')
    let repeatedPassword = document.getElementById('edit_repeatPassword')
    try{
        if(newPassword != "" && new newPassword == repeatedPassword){
            return await userService.changePassword(user.value, newPassword).then(response => {        
                user.value = JSON.parse(localStorage.getItem('user')).userDTO
                document.getElementById('edit_password').value = ""
                document.getElementById('edit_repeatPassword').value = ""
                errorMessage.value =""
            })
        }else{
            errorMessage.value = "Passwords don't match"
        }
    }catch{
        errorMessage.value = "Password could not be changed. Please try agein!"
    }

}

async function changeEmail(){
    let newEmail = document.getElementById('edit_email').value
    try{
        if(newEmail != ""){
            return await userService.changeEmail(user.value, newEmail).then(response => {
                user.value = JSON.parse(localStorage.getItem('user')).userDTO
                document.getElementById('edit_email').value = ""
                errorMessage.value =""
            })
        }

    }catch{
        errorMessage.value = "Email could not be changed. Please try agein!"
    }

}

async function deleteUser(){
    try{
        return await userService.delete(user.value).then(response => {
            errorMessage.value =""
        })
    }catch{
        errorMessage.value = "User could not be deleted. Please try agein!"
    }
}

</script>