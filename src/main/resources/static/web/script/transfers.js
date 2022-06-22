Vue.createApp({
  data() {
    return {

      dataClient: [],
      contenido: [],
      account: "",
      vin: "VIN",
      destinationAccountNumber: "",
      destinationAccount: "",
      destination:"",
      filtrado: [],
      originAccount: "",
      amount: 0,
      description: "",

    }
  },


  created() {
    axios.get(`/api/clients/current`)
      .then(data => {
        this.dataClient = data.data
        this.contenido = data.data.accounts
        this.loans = data.data.loans

        console.log(this.contenido)
        console.log(this.filtrado)

      })

  },
  methods: {

    newTransfer() {
      var regExp = /[a-zA-Z]/;

      Swal.fire({
        title: 'Do you want to make the transaction?',
        icon: 'warning',
        showDenyButton: true,
        confirmButtonText: 'Make the transaction',
        denyButtonText: `Don't make`,
      }).then((result) => {
        if (result.isConfirmed) {
          if (this.account == "ajena" && regExp.test(this.destinationAccount)) {

            Swal.fire({
              icon: 'error',
              title: 'Invalid Destination account',
              text: 'Account number can not contain leters',
            })

          } else {
            if (this.account == "ajena") {
              this.destinationAccountNumber = this.vin + this.destinationAccount;
            } else {
              this.destinationAccountNumber = this.destinationAccount
            }
            if (this.amount <= 0) {
              Swal.fire({
                icon: 'error',
                title: 'Invalid Amount',
                text: 'Amount can not be negative number or 0',

              })
            } else {
              axios.post('/api/transaction', `amount=${this.amount}&originAccount=${this.originAccount}&destinationAccount=${this.destinationAccountNumber}&description=${this.description}`,
                { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(response =>
                  Swal.fire(
                    'Transaction Aprooved',
                    'The transaction was succefulled made',
                    'success'
                  )
                    .then(response => window.location.href = '/web/accounts.html', 5000))
                .catch(error => console.log(error))
            }
          }
        } else if (result.isDenied) {
          Swal.fire('Transaction canceled', '', 'info')
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

    filtro() {
      this.filtrado = this.contenido.filter(accout => accout.number !== originAccount)
      return this.filtrado
    },

  }

}).mount('#app')