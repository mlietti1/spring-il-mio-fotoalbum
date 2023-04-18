const { createApp } = Vue;

createApp({
  data() {
    return {
      message: "Hello Vue",
      apiPostUrl: "http://localhost:8080/api/posts",
      apiMsgUrl: "http://localhost:8080/api/messages",
      posts: [],
      keyword: "",
      showForm: false,
      isLoading: true,
      email: "",
      text: "",
    };
  },

  methods: {
    loadData() {
      axios.get(this.apiPostUrl + `?q=${this.keyword}`).then((response) => this.posts = response.data);
      this.isLoading = false;
        
    },

    sendMsg() {
      axios
        .post(this.apiMsgUrl, {
          email: this.email,
          text: this.text
        })
        .catch((error) => console.log(error))
        .then((response) => {
          console.log(response.data);
          this.email = "";
          this.text = "";
          this.loadData();
        });
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