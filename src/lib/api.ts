const API_BASE =
  (import.meta.env.VITE_API_URL && import.meta.env.VITE_API_URL.trim().replace(/\/$/, '')) ||
  'http://localhost:8081';

const buildUrl = (path: string) => {
  const url = `${API_BASE}${path}`;
  // Логируем всегда в продакшене для диагностики
  console.log('[API] Request URL:', url, '| API_BASE:', API_BASE, '| VITE_API_URL:', import.meta.env.VITE_API_URL || 'NOT SET');
  return url;
};

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
      const apiUrl = buildUrl('/api/products');
      const errorMsg = `Network error: Unable to reach API at ${apiUrl}. VITE_API_URL=${import.meta.env.VITE_API_URL || 'not set'}. Please check if the backend is running and VITE_API_URL is configured.`;
      console.error('[API Error]', errorMsg);
      throw new Error(errorMsg);
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