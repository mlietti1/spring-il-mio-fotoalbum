const { createApp } = Vue;

createApp({
  data() {
    return {
      message: "Hello Vue",
      apiUrl: "http://localhost:8080/api/posts",
      posts: [],
      keyword: "",
      showForm: false,
      isLoading: true,
    };
  },

  methods: {
    loadData() {
      axios.get(this.apiUrl + `?q=${this.keyword}`).then((response) => this.posts = response.data);
      this.isLoading = false;
        
    },

    sendMsg() {
      axios
        .post(this.apiUrl, {
          //name: this.name,
          //description: this.description,
          //price: this.price
        })
        .then(() => this.loadData());
    },
    
    scrollDown(){
      const container = document.getElementById('scroll');
      container.scrollBy(0, 700);
    },

    toggleForm(){
      this.showForm = !this.showForm;
    }
  },

  mounted() {
    this.loadData();
  }
}).mount("#app")