Component({
  properties: {
    show: { type: Boolean, value: false }
  },
  data: {
    moodStatus: 1,
    content: ''
  },
  methods: {
    chooseMood(e) {
      this.setData({ moodStatus: Number(e.currentTarget.dataset.mood) });
    },
    onInput(e) {
      this.setData({ content: e.detail.value || '' });
    },
    submit() {
      this.triggerEvent('submit', {
        moodStatus: this.data.moodStatus,
        content: this.data.content
      });
    },
    close() {
      this.triggerEvent('close');
    }
  }
});
