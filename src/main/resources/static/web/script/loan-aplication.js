
Vue.createApp({
    data() {
        return {
            dataClient: {},
            prestamo : "",
            loans: [],
            loanId: 0,
            cuotas: [],
            payment: "",
            amount: 0,
            accounts: [],
            accountNumber: "",
            amountPerPayment: 0,
            loanAplicationDTO: {},
            prePrestamo:{},
            prestamo:{},
            pcte:0,
        }
    },
    created() {

        axios.get(`http://localhost:8080/api/clients/current`)
            .then(result => {
                this.dataClient = result.data


                axios.get(`http://localhost:8080/api/loans`)
                    .then(data => {
                        this.loans = data.data
                        this.accounts = this.dataClient.accounts
                        console.log(this.loans)
                        console.log(this.accounts)
           
                    })

            })
    },
    methods: {

        newLoan() {

            var regExp = /[a-zA-Z]/;
            
            Swal.fire({
                title: 'Are you sure to make the loan?',
                showDenyButton: true,
                icon:'info',
                confirmButtonText: 'Get Loan',
                denyButtonText: `Cancel Loan`,
            }).then((result) => {

                if (result.isConfirmed) {


                    if (this.payment <= 0 || regExp.test(this.payment)) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Payment',
                            text: 'Select the payments',
                        })
                    } else if (this.accountNumber == "") {
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Account',
                            text: 'Destination account invalid',
                        })
                    } else if (this.amount <= 0) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Amount',
                            text: 'Amount can not be negative number or 0',
                        })
                    } else {

                        this.loanAplicationDTO = {

                            idLoan: this.loanId,
                            amount: this.amount,
                            payments: this.payment,
                            number: this.accountNumber
                        };

                        axios.post('http://localhost:8080/api/loan', this.loanAplicationDTO)
                            .then(respuesta => {
                                Swal.fire(
                                    'Loan accepted',
                                    'Return to see it on your account',
                                    'success'
                                )
                                    .then(next => window.location.href = '/web/accounts.html', 5000)
                            })
                            .catch(error => {
                                console.log(error)
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Something went Wrong',
                                    text: 'Reset the form and try again',

                                })
                            })
                    }

                } else if (result.isDenied) {
                    Swal.fire('Loan Canceled', '', 'info')
                }
            })

        },


        logOut() {

            axios.post('/api/logout')
                .then(response =>
                    window.location.href = '/web/index.html', 5000
                );
        },
    },
    computed: {

        filterLoan(){
            this.prePrestamo = this.loans.filter(loan => loan.id == this.loanId)
            this.prestamo = this.prePrestamo[0]

            if(this.prestamo != null){
                console.log(this.prestamo)
                this.cuotas = this.prestamo.payment
               
            
            }
            
        },

        interest(){

            if(this.prestamo != null){

                this.pcte = this.prestamo.interest * 100

                return this.pcte
            }

        },

        amountPayment() {
            parseInt(this.payment)
            
            if(this.prestamo != null){
            this.amountPerPayment = (this.amount * (this.prestamo.interest + 1)) / this.payment
                    }
            return this.amountPerPayment
        },
    },
}).mount('#app')

console.log(0.1 + 0.2)