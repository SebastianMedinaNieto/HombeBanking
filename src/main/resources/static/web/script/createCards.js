Vue.createApp({
  data() {
    return {
      cardColor: "",
      cardType: "",
      cards: [],
      debit: [],
      credit: [],
    }
  },

  created() {

    axios.get(`http://localhost:8080/api/clients/current`)
      .then(data => {
        this.dataClient = data.data
        this.cards = data.data.cards

        console.log(this.cards)
        this.credit = this.cards.filter(card => card.cardType == "CREDIT")
        this.debit = this.cards.filter(card => card.cardType == "DEBIT")
      }
      )
  },
  methods: {

    CreateCard() {

      if (this.cardType == "" || this.cardColor == "") {

        Swal.fire(
          'Invalid Data',
          'Missing data',
          'error'
        )
      } else if (this.cardType == "DEBIT" && this.debit.length >= 3) {
        Swal.fire(
          'Limit of cards',
          'You already have 3 debit cards',
          'error'
        )
      } else if (this.cardType == "CREDIT" && this.credit.length >= 3) {
        Swal.fire(
          'Limit of cards',
          'You already have 3 credit cards',
          'error'
        )
      } else {

        Swal.fire({
          title: 'are you sure to create a card?',
          icon: 'warning',
          showDenyButton: true,
          confirmButtonText: 'Create',
          denyButtonText: `Don't create`,
        }).then((result) => {
          if (result.isConfirmed) {

            axios.post('http://localhost:8080/api/clients/current/cards', `cardColor=${this.cardColor}&cardType=${this.cardType}`,
            { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
              .then(response => {
                Swal.fire('Created!', '', 'success')
                .then(response => window.location.href = '/web/cards.html', 5000)
              })
          } else if (result.isDenied) {
            Swal.fire('Canceled', '', 'error')
          }
        })
      }
    },

    logOut() {

      axios.post('/api/logout')
        .then(response =>
          window.location.href = '/web/index.html', 5000
        );
    },
  }
}).mount('#app')

