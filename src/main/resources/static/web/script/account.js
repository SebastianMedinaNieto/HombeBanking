
Vue.createApp({
    data() {
        return {
            transaccionesTotales: [],
            contenido: [],
        }
    },


    created() {
        const URLPARAMS = new URLSearchParams(window.location.search)
        const MYPARAM = URLPARAMS.get(`id`)
        axios.get("http://localhost:8080/api/clients/current/accounts/" + MYPARAM)

            .then(result => {
                this.transaccionesTotales = result.data.transactions
                this.contenido = result.data
                console.log(this.transaccionesTotales)
            })
    },
    methods: {



        logOut() {

            axios.post('/api/logout')
                .then(response =>
                    window.location.href = '/web/index.html', 5000
                );
        },
    },

    computed: {

        ordenarPorId() {
            let transaccionesOrdenadas = []
            transaccionesOrdenadas = this.transaccionesTotales.filter(transa => transa.id).sort(function (a, b) { return a.id - b.id })
            this.transaccionesTotales = transaccionesOrdenadas
        },


    },


}).mount('#app')

