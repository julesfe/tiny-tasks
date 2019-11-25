import {getToken} from '../auth';

const authorization = () => ({
  Authorization: `Bearer ${getToken()}`,
});

export const getRequest = () => {
  return {
    credentials: 'include',
    headers: {
      ...authorization(),
    },
  }
};

export const postRequest = body => {
  return {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json',
      ...authorization(),
    },
    body: JSON.stringify(body),
  }
};

export const deleteRequest = () => {
  return {
    method: 'DELETE',
    credentials: 'include',
    headers: {
      ...authorization(),
    },
  }
};
