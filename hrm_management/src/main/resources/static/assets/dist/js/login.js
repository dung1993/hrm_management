


// document.querySelector('.img__btn').addEventListener('click', function() {
//     document.querySelector('.cont').classList.toggle('s--signup');
// });

function showPassword1() {
    let password = document.querySelector("#passwordLogin");
    if (password.type === "password") {
        password.type = "text";
    } else {
        password.type = "password";
    }
}
function showPassword2() {
    let password = document.querySelector("#passwordRegister");
    if (password.type === "password") {
        password.type = "text";
    } else {
        password.type = "password";
    }
}