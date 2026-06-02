import { jest } from '@jest/globals';
import fs from 'fs';

describe('jobController', () => {
  beforeEach(async () => {
    jest.resetModules();
    jest.clearAllMocks();
    await jest.unstable_mockModule('child_process', () => ({
      spawn: jest.fn(() => ({ unref: jest.fn() }))
    }));
  });

  test('startProcessingJob returns 400 when query params are missing', async () => {
    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: {} };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(400);
    expect(res.json).toHaveBeenCalledWith({
      error: 'Missing target color and a threshold to find the mander'
    });
  });

  test('startProcessingJob returns 404 when video not found', async () => {
    jest.spyOn(fs, 'existsSync').mockImplementation(() => false);

    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: 1, threshold: 2 } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(404);
    expect(res.json).toHaveBeenCalledWith({ error: 'No Mander videos found' });
  });

  test('startProcessingJob creates job and returns 202 with jobID', async () => {
    jest.spyOn(fs, 'existsSync').mockImplementation(() => true);

    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: 115938, threshold: 115 } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(202);
    const jsonArg = res.json.mock.calls[0][0];
    expect(jsonArg.jobID).toBeDefined();
  });

  test('getJobStatus returns 404 for invalid job id', async () => {
    const { getJobStatus } = await import('../controllers/jobController.js');

    const req = { params: { jobID: 'fake-job-id' } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await getJobStatus(req, res);

    expect(res.status).toHaveBeenCalledWith(404);
    expect(res.json).toHaveBeenCalledWith({ error: 'You have no Job!!' });
  });
});
