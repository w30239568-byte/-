const { request } = require('../../../utils/request');

Page({
  data: {
    signMsg: {},
    checkedDays: [],
    showSignDialog: false,
    showAchieveDialog: false,
    pendingTaskId: null,
    taskList: []
  },

  onShow() {
    this.loadSignData();
  },

  async loadSignData() {
    const app = getApp();
    try {
      const signMsg = await request({
        url: '/api/activity/getSignMsg',
        data: { activityId: app.globalData.activityId, userId: app.globalData.userId }
      });
      const taskList = await request({
        url: '/api/activity/getTaskList',
        data: { activityId: app.globalData.activityId, userId: app.globalData.userId }
      });
      this.setData({
        signMsg: signMsg || {},
        taskList: taskList || [],
        pendingTaskId: (taskList && taskList[0] && taskList[0].id) || null,
        showAchieveDialog: !!(signMsg && signMsg.ifComplete && signMsg.priceList && signMsg.priceList.length)
      });
    } catch (e) {}
  },

  openSignDialog() {
    if (!this.data.pendingTaskId) {
      wx.showToast({ title: '暂无待打卡任务', icon: 'none' });
      return;
    }
    this.setData({ showSignDialog: true });
  },

  closeSignDialog() {
    this.setData({ showSignDialog: false });
  },

  closeAchieveDialog() {
    this.setData({ showAchieveDialog: false });
  },

  async submitSign(e) {
    const app = getApp();
    const payload = e.detail || {};
    try {
      await request({
        url: '/api/activity/signIn',
        method: 'POST',
        write: true,
        data: {
          activityId: app.globalData.activityId,
          taskId: this.data.pendingTaskId,
          userId: app.globalData.userId,
          moodStatus: payload.moodStatus,
          content: payload.content,
          resource: '',
          aiComment: '',
          isOpen: 1
        }
      });
      wx.showToast({ title: '打卡成功', icon: 'success' });
      this.setData({ showSignDialog: false });
      this.loadSignData();
    } catch (err) {}
  }
});
