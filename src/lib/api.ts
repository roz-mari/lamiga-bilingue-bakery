const API_BASE =
  (import.meta.env.VITE_API_URL && import.meta.env.VITE_API_URL.trim().replace(/\/$/, '')) ||
  'http://localhost:8081';

const buildUrl = (path: string) => `${API_BASE}${path}`;

export type Product = {
  id: number;
  name_en: string;
  name_es: string;
  description_en: string;
  description_es: string;
  price: number;
  imageUrl: string;
};

export async function getProducts(): Promise<Product[]> {
  try {
    const url = buildUrl('/api/products');
    const res = await fetch(url, { cache: 'no-store' });
    if (!res.ok) {
      throw new Error(`Failed to load products: ${res.status} ${res.statusText}`);
    }
    return res.json();
  } catch (error) {
    if (error instanceof TypeError && error.message.includes('fetch')) {
      throw new Error(`Network error: Unable to reach API at ${buildUrl('/api/products')}. Please check if the backend is running.`);
    }
    throw error;
  }
}

export async function sendContact(data: { name: string; email: string; message: string }) {
  const res = await fetch(buildUrl('/api/contact'), {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  if (!res.ok) throw new Error('Failed to send message');
  return true;
}