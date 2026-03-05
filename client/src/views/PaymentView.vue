<script setup lang="ts">
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

const movieId = route.params.id as string;
const defaultAmount = 9.99;

const isSubmitting = ref(false);
const message = ref('');
const isError = ref(false);

const paymentData = ref({
  cardNumber: '',
  expiryDate: '',
  cvv: '',
  amount: defaultAmount,
  reservationId: parseInt(movieId) || 0
});

const processPayment = async () => {
  isSubmitting.value = true;
  message.value = 'Traitement du paiement...';
  isError.value = false;

  try {
    const response = await fetch(`${GATEWAY_URL}/payments/validate`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(paymentData.value)
    });

    const isValid = await response.json();

    if (response.ok && isValid) {
      message.value = 'Paiement validé ! Redirection...';
      isError.value = false;
      setTimeout(() => router.push('/catalog'), 2000);
    } else {
      isError.value = true;
      message.value = 'Paiement refusé par la banque.';
    }
  } catch (err) {
    isError.value = true;
    message.value = err instanceof Error ? err.message : 'Erreur lors du paiement';
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<template>
  <div class="payment-view">
    <button @click="router.back()" class="back-btn">← Retour</button>

    <div class="payment-card">
      <h1>Finaliser la location</h1>
      <p class="summary">Montant à régler : <strong>{{ paymentData.amount }} €</strong></p>

      <form @submit.prevent="processPayment" class="payment-form">
        <div class="form-group">
          <label>Numéro de carte</label>
          <input
            v-model="paymentData.cardNumber"
            type="text"
            placeholder="1234 5678 9101 1121"
            required
          />
        </div>

        <div class="row">
          <div class="form-group">
            <label>Date d'expiration</label>
            <input
              v-model="paymentData.expiryDate"
              type="text"
              placeholder="MM/YY"
              required
            />
          </div>
          <div class="form-group">
            <label>CVV</label>
            <input
              v-model="paymentData.cvv"
              type="text"
              placeholder="123"
              required
            />
          </div>
        </div>

        <button type="submit" :disabled="isSubmitting" class="pay-btn">
          {{ isSubmitting ? 'Validation en cours...' : 'Payer maintenant' }}
        </button>

        <p v-if="message" :class="{ 'error-msg': isError, 'success-msg': !isError }">
          {{ message }}
        </p>
      </form>
    </div>
  </div>
</template>

<style scoped>
.payment-view { max-width: 400px; margin: 2rem auto; padding: 1rem; border: 1px solid #ccc; border-radius: 8px; }
.payment-form { display: flex; flex-direction: column; gap: 1rem; margin-top: 1rem; }
.form-group { display: flex; flex-direction: column; gap: 0.5rem; }
.row { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
input { padding: 0.8rem; border: 1px solid #ddd; border-radius: 4px; }
.pay-btn { background-color: #42b983; color: white; padding: 1rem; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
.pay-btn:disabled { background-color: #ccc; }
.error-msg { color: red; margin-top: 1rem; }
.success-msg { color: green; margin-top: 1rem; }
.back-btn { background: none; border: none; color: #666; cursor: pointer; margin-bottom: 1rem; }
</style>
