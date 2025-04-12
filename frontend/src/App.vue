<template>
  <router-view />
</template>

<script setup>
</script>

  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  background-color: var(--bg-color);
  background-image: var(--dance-pattern);
  color: var(--text-color);
  margin: 0;
  min-height: 100vh;

.app-container {
  position: relative;
  min-height: 100vh;
  padding-bottom: 120px;
}

.welcome-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 3rem 4rem;
  border-radius: 1.5rem;
  width: 80%;
  max-width: 800px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

.welcome-overlay h1 {
  color: var(--primary-color);
  margin-bottom: 1.5rem;
  font-size: 2.5rem;
}

.welcome-overlay p {
  color: var(--text-color);
  font-size: 1.2rem;
  margin-bottom: 2.5rem;
}

.welcome-actions {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
}

.start-button {
  background-color: var(--primary-color);
  color: white;
  padding: 1rem 2.5rem;
  border-radius: 30px;
  font-weight: bold;
  font-size: 1.2rem;
  text-decoration: none;
  transition: all 0.3s;
}

.start-button:hover {
  background-color: var(--secondary-color);
  transform: translateY(-2px);
}

.register-hint {
  color: var(--text-color);
  font-size: 1rem;
}

.register-hint a {
  color: var(--secondary-color);
  text-decoration: underline;
}

nav {
  padding: calc(var(--spacing-unit) * 1.5);
  text-align: center;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  position: fixed;
  bottom: 0;
  width: 100%;
}

nav a {
  margin: 0 calc(var(--spacing-unit) * 0.5);
  color: var(--primary-color);
  text-decoration: none;
  font-weight: bold;
  font-size: 1.1rem;
  transition: all 0.3s;
  padding: 0.5rem 1.5rem;
  border-radius: 30px;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}

.nav-icon {
  font-size: 1.2rem;
}

.nav-divider {
  color: var(--secondary-color);
  margin: 0 1rem;
}

nav a:hover {
  color: var(--secondary-color);
}

nav a.router-link-active {
  color: var(--secondary-color);
  border-bottom: 2px solid var(--primary-color);
}

.features-container {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 2rem;
  padding: 4rem 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  background: var(--feature-bg);
  border-radius: 1.5rem;
  padding: 2rem;
  width: 280px;
  text-align: center;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 1.5rem;
}

.feature-card h3 {
  color: var(--primary-color);
  margin-bottom: 1rem;
}

.feature-card p {
  color: var(--text-color);
  line-height: 1.6;
}
