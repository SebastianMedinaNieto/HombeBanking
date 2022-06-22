Vue.createApp({
  data() {
    return {

      dataClient: [],
      accounts: [],
      client: {},
      firstName: "",
      lastName: "",
      email: "",
      person: 0,
      loans: [],
      id: 0,
      tipoDeCuenta: "",
    }
  },


  created() {
    axios.get(`http://localhost:8080/api/clients/current`)
      .then(data => {
        this.dataClient = data.data
        this.accounts = data.data.accounts
        this.loans = data.data.loans
        console.log(this.accounts)


      })
  },
  methods: {


    logOut() {

      axios.post('/api/logout')
        .then(response =>
          window.location.href = '/web/index.html', 5000
        );

    },

    deleteAccount(account) {

      Swal.fire({
        title: 'Do you want to delete the account?',
        icon: "warning",
        showDenyButton: true,
        confirmButtonText: 'delete',
        denyButtonText: `Don't delete`,
      }).then((result) => {
        if (result.isConfirmed) {
          if(account.balance >0){
            Swal.fire(
              'Can not delete',
              'Can not delete account that balance is not 0',
              'error'
            )
          }else{
            this.id = account.id

            axios.patch("http://localhost:8080/api/clients/current/accounts", `id=${this.id}`)
              .then(result => Swal.fire('Deleted!', '', 'success'))
              .then(result => window.location.href = '/web/accounts.html')
          }


        } else if (result.isDenied) {
          Swal.fire('Canceleded', '', 'info')
        }
      })


    },

    newAccount() {

      if (this.accounts.length >= 3) {
        Swal.fire(
          'Limit of Accounts',
          'You already have 3 accounts',
          'error'
        )
      } else {
        Swal.fire({
          title: 'are you sure to create a account?',
          icon: 'warning',
          showDenyButton: true,
          confirmButtonText: 'Create',
          denyButtonText: `Don't create`,
        }).then((result) => {

          if (result.isConfirmed) {

            axios.post('http://localhost:8080/api/clients/current/accounts',`accountType=${this.tipoDeCuenta}`,
              { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
              .then(response => {
                Swal.fire('Created!', '', 'success')
                  .then(response => window.location.href = '/web/accounts.html')
              })
          } else if (result.isDenied) {
            Swal.fire('Canceled', '', 'error')
          }
        })
      }
    }

  }
}).mount('#app')