<script setup>
import { onMounted, ref } from 'vue'

const now = ref(new Date())

function tick() {
    now.value = new Date()
}

onMounted(() => {
    window.setInterval(tick, 1000);
})
</script>

<template>
  <div class="py-10 px-5">
    <div>{{ now }}</div>
  </div>
</template>
