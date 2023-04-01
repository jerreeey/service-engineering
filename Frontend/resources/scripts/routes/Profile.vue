<template>
<div class="spacer">
    <div class="profile stack stack5">
    <h1>My Profile</h1>
    <div class="stack stack5 stack--row">
        <h2 class="h6">E-Mail:</h2>
        <div>jeremias.rauwolf@gmail.com</div>
        <div>        
            <label for="edit_email">New email</label>
            <input id="edit_email" type="email">
        </div>
        <button class="button button--primary">Edit</button>
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
        <button class="button button--primary">Edit</button>
    </div>
    <button class="button button--delete">Delete account</button>
</div>
</div>
</template>