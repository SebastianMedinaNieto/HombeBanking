
Vue.createApp({
    data() {
        return {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            client: {},

        }
    },


    created() {



    },
    methods: {
        logIn() {

            console.log(this.email, this.password)
    
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response => {
                    if (this.email.includes("@admin")) {
                        window.location.href = '/web/loanCreation.html'
                    } else {
                        window.location.href = '/web/accounts.html'
                    }
            });

        },

        register() {

            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response =>
                    axios.post('/api/login', `email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(response =>
                            window.location.href = '/web/accounts.html', 5000

                        ))
        },
    },

    computed: {




    },


}).mount('#app')

const signInBtn = document.getElementById("signIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");

signInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", (e) => e.preventDefault());
secondForm.addEventListener("submit", (e) => e.preventDefault());
