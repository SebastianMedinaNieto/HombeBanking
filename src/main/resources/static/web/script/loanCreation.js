
Vue.createApp({
    data() {
        return {
            nameCatcher:"",
            name:"",
            amount: 0,
            taxes: 0,
            addPayment:0,
            payments: [],
        }
    },


    created() {

    },
    methods: {
        
        addLoan(){
            this.payments.push(this.addPayment)
            return this.payments
        },
        createLoan(){

            var regExp = /[a-zA-Z]/;
            console.log(this.payments)
            Swal.fire({
                title: 'Do you want to create the loan?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'create',
                denyButtonText: `Don't create`,
              }).then((result) => {
                if (result.isConfirmed) {
                    if( this.name == " "){
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Name',
                            text: 'Try other please',
                        })
                    } else if (regExp.test(this.taxes)){
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Character',
                            text: 'Invalid Character in taxes',
                        })
                    } else if (this.amount <= 0){
                        Swal.fire({
                            icon: 'error',
                            title: 'Invalid Ammount',
                            text: 'Amount must be more than 0',
                        })
                    } else{
                        axios.post('http://localhost:8080/api/admin/loan',`maxAmount=${this.amount}&name=${this.name}&interest=${this.taxes}&payments=${this.payments}`)
                        .then(response =>  Swal.fire({
                            icon: 'success',
                            title: 'Loan created',
                            text: 'Good job admin',
                        }))
                    }

                } else if (result.isDenied) {
                  Swal.fire('Changes are not saved', '', 'info')
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

    },
}).mount('#app')

