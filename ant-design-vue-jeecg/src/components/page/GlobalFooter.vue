<template>
  <div class="footer">
    <div class="copyright">Copyright &copy; {{year}} Boo AI 出品</div>
  </div>
</template>

<script>
  import { formatDate } from '@/utils/util.js'

  export default {
    name: "LayoutFooter",
    data () {
      return {
        year: formatDate(new Date().getTime(), 'yyyy')
      }
    }
  }
</script>

<style lang="less" scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;

    .links {
      margin-bottom: 8px;

      a {
        color: rgba(0, 0, 0, .45);

        &:hover {
          color: rgba(0, 0, 0, .65);
        }

        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
    }
  }
</style>