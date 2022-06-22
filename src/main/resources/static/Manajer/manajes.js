


Vue.createApp({
    data() {
      return {
        message: 'Hello Vue!',
        dataClient: [],
        contenido:[],
        client:{},
        firstName:"",
        lastName:"",
        email:"",
        person:0,
        
      }
    },
    

    created(){
        axios.get(`http://localhost:8080/api/clients`)
        .then(data =>{
            this.dataClient = data.data
            this.contenido = data.data

            console.log(this.dataClient[0].id)
            console.log(this.contenido)



         }
            
         )



    
    



    },
    methods: {
        addClient(){
            if(this.firstName !="" && this.lastName !=""){

            this.client = {



                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email
            }


                axios.post(`http://localhost:8080/rest/clients`, this.client)
                } else {
                    window.alert("dato invalido, intente denuevo") ;
                }





        },

        deleteClient(persona){
            this.person = persona.id;

            console.log(`http://localhost:8080/rest/clients/${this.person}`)

             axios.delete(`http://localhost:8080/rest/clients/${this.person}`);




        },

    },



  }).mount('#app')