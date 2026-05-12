Component({
  properties: {
    show: { type: Boolean, value: false },
    rewards: { type: Array, value: [] }
  },
  methods: {
    close() {
      this.triggerEvent('close');
    }
  }
});
