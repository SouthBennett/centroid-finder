import { jest } from '@jest/globals';
import fs from 'fs';
import { getVideoPath } from '../utils/pathUtils.js';

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

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: '#FFAABB', threshold: '115' } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(404);
    expect(res.json).toHaveBeenCalledWith({ error: 'No Mander videos found' });
  });

  test('startProcessingJob creates job and returns 202 with jobID', async () => {
    process.env.JAR_PATH = '/tmp/fake.jar';
    jest.spyOn(fs, 'existsSync').mockImplementation((p) => {
      if (p === getVideoPath('ensantina.mp4')) return true;
      if (p === process.env.JAR_PATH) return true;
      return false;
    });

    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: '#FFAABB', threshold: '115' } };
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

  test('startProcessingJob returns 400 for invalid hex targetColor', async () => {
    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: 'zzzzzz', threshold: '115' } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(400);
    expect(res.json).toHaveBeenCalledWith({ error: 'Invalid targetColor hex format' });
  });

  test('startProcessingJob returns 400 for invalid threshold', async () => {
    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: '#FFAABB', threshold: 'abc' } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(400);
    expect(res.json).toHaveBeenCalledWith({ error: 'Invalid threshold value' });
  });

  test('startProcessingJob returns 500 when JAR missing', async () => {
    process.env.JAR_PATH = '/tmp/missing.jar';
    jest.spyOn(fs, 'existsSync').mockImplementation((p) => {
      if (p === getVideoPath('ensantina.mp4')) return true;
      if (p === process.env.JAR_PATH) return false;
      return false;
    });

    const { startProcessingJob } = await import('../controllers/jobController.js');

    const req = { params: { filename: 'ensantina.mp4' }, query: { targetColor: '#FFAABB', threshold: '115' } };
    const res = {
      status: jest.fn().mockReturnThis(),
      json: jest.fn().mockReturnThis()
    };

    await startProcessingJob(req, res);

    expect(res.status).toHaveBeenCalledWith(500);
    expect(res.json).toHaveBeenCalledWith({ error: 'JAR file not found' });
    delete process.env.JAR_PATH;
  });
});
