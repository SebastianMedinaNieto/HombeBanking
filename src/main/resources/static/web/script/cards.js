
Vue.createApp({
    data() {
        return {
            transaccionesTotales: [],
            tarjetas: [],
            tarjetasDebito: [],
            tarjetasCredito: [],
            tarjetaId: 0,
        }
    },
    created() {
        axios.get(`/api/clients/current`)
            .then(result => {
                this.tarjetas = result.data.cards
                this.tarjetasDebito = this.tarjetas.filter(tarjeta => tarjeta.cardType == "DEBIT")
                this.tarjetasCredito = this.tarjetas.filter(tarjeta => tarjeta.cardType == "CREDIT")
            }
            )
    },
    methods: {

        deleteCard(tarjeta) {

            this.tarjetaId = tarjeta.id
            
            Swal.fire({
                title: 'Do you want to delete the card?',
                icon: "warning",
                showDenyButton: true,
                confirmButtonText: 'Delete',
                denyButtonText: `Don't Delete`,
              }).then((result) => {
            
                if (result.isConfirmed) {
                    axios.patch("/api/clients/current/cards",`id=${this.tarjetaId}`)
                    .then(result =>{ Swal.fire('Deleted!', '', 'success')
                    .then(result => window.location.href = "/web/cards.html")})
                   

                } else if (result.isDenied) {
                  Swal.fire('Canceled', '', 'info')
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

